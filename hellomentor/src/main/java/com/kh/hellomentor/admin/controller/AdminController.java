package com.kh.hellomentor.admin.controller;

import com.kh.hellomentor.admin.model.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@Slf4j
public class AdminController {

    @Autowired
    private AdminService aService;

    @GetMapping("/admin/selectList")
    public String selectList(Model model) {
        //public String selectMList( @RequestParam Map<String, Object> paramMap, Model model) {
        int total = aService.selectListCount();
        model.addAttribute("aTotal", total);

        int stotal = aService.selectSListCount();
        model.addAttribute("sTotal", stotal);

        int rtotal = aService.selectRListCount();
        model.addAttribute("rTotal", rtotal);

        int itotal = aService.selectIListCount();
        model.addAttribute("iTotal", itotal);

        return "admin/admin-main";
    }

    /*
     * @GetMapping("/admin/selectMList") public String selectSList(Model model) {
     *
     * return "admin/admin-main"; }
     *
     * @GetMapping("/admin/selectMList") public String selectRList(Model model) {
     * //List<Report> rList = aService.selectRList();
     *
     * return "admin/admin-main"; }
     *
     * @GetMapping("/admin/selectMList") public String selectIList( Model model) {
     *
     * return "admin/admin-main"; }
     */
}
   
   
