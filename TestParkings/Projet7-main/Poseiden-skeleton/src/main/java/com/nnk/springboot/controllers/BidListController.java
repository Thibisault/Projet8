package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.service.BidlistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;


@Controller
public class BidListController {

    @Autowired
    BidlistService bidlistService;

    /**
     * Permet de récupérer, de stocker et d'afficher la liste de tout les BidList de la BDD
     * @param model
     * @return
     */
    @RequestMapping("/bidList/list")
    public String home(Model model)
    {
        model.addAttribute("allBidList", bidlistService.chercherTouteLesBidList());
        return "bidList/list";
    }

    /**
     * Permet d'afficher le formulaire de création d'un nouveau BidList
     * @param bid
     * @return
     */
    @GetMapping("/bidList/add")
    public String addBidForm(BidList bid) {
        return "bidList/add";
    }

    /**
     * Permet de créer un nouveau BidList dans la BDD
     * @param bidList
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/bidList/validate")
    public String validate(@Valid BidList bidList, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "bidList/add";
        }else{
            bidlistService.creerNewBidlist(bidList);
            model.addAttribute("newBidlist", bidList);
        }
        return "bidList/add";
    }

    /**
     * Permet d'afficher le formulaire de mise à jour d'un BidList préci dans la BDD grâce à son ID
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/bidList/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("updateBidList", bidlistService.chercherByBidListId(id));
        return "bidList/update";
    }

    /**
     * Permet de mettre à jour un BidList préci dans la BDD grâce à son ID
     * @param id
     * @param bidList
     * @param result
     * @param model
     * @return
     */
    @PostMapping("/bidList/update/{id}")
    public String updateBid(@PathVariable("id") Integer id, @Valid BidList bidList,
                            BindingResult result, Model model) {
        if(result.hasErrors()){
            return "redirect:/bidList/update/{id}";
        }else{
            bidList.setBidListId(id);
            bidlistService.creerNewBidlist(bidList);
            model.addAttribute("allBidList", bidlistService.chercherTouteLesBidList());
        }
        return "redirect:/bidList/list";
    }

    /**
     * Permet de supprimer un BidList dans la BDD
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/bidList/delete/{id}")
    public String showDeleteBid(@PathVariable("id") Integer id, Model model) {
        bidlistService.supprimerBidList(bidlistService.chercherByBidListId(id));
        model.addAttribute("allBidList", bidlistService.chercherTouteLesBidList());
        return "redirect:/bidList/list";
    }
}
