package com.atguigu.controller;
import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.atguigu.base.BaseController;
import com.atguigu.entity.Role;
import com.atguigu.service.RoleService;
import com.github.pagehelper.PageInfo;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/role")
public class RoleController extends BaseController {
    private static final String PAGE_ASSIGNSHOW = "role/assignShow";
    private static final String PAGE_CREATE = "role/create";
    @Reference
    private RoleService roleService;
    private final static String PAGE_INDEX = "role/index";
    private final static String PAGE_SUCCESS = "common/successPage";
    private final static String PAGE_EDIT = "role/edit";
    private final static String LIST_ACTION = "redirect:/role";
  @PreAuthorize("hasAnyAuthority('role.show')")
    @RequestMapping
    public String index(@RequestParam Map<String,Object> filters ,Model model) {
        //filters就是获取到的客户端的请求参数，里边可能包含:pageNum、pageSize、roleName
        //1. 判断请求参数中是否传入了pageNum和pageSize，如果没有则给其赋默认值
        if (filters.get("pageNum") == null || "".equals(filters.get("pageNum"))) {
            filters.put("pageNum",1);
        }
        if (filters.get("pageSize") == null || "".equals(filters.get("pageSize"))) {
            filters.put("pageSize",10);
        }

        //2. 调用业务层的方法查询分页信息
        PageInfo<Role> pageInfo = roleService.findPage(filters);
        //3. 将分页数据存储到请求域
        model.addAttribute("page",pageInfo);
        //4. 将搜索条件存储到请求域
        model.addAttribute("filters",filters);
        return PAGE_INDEX;

    }
@PreAuthorize("hasAnyAuthority('role.create')")
 @PostMapping("/save")
   public String instert (Role role, Model model){

        roleService.insert(role);


     //3.显示 common/successPage.html
     return  successPage(model,"新增角色成功");

   }

@PreAuthorize("hasAnyAuthority('role.edit')")
   @GetMapping("/edit/{id}")
   public String edit (@PathVariable Long id , Model model){
       Role role = roleService.getById(id);
       model.addAttribute("role",role);
       return PAGE_EDIT;
   }
    @PreAuthorize("hasAnyAuthority('role.edit')")
    @PostMapping ("/update")
    public String edit (Role role, Model model){
        roleService.update(role);
        return successPage(model,"更新角色成功");
    }
    @PreAuthorize("hasAnyAuthority('role.delete')")
    @GetMapping("/delete/{id}")
    public String delete (@PathVariable Long id , Model model){
        roleService.delete(id);

        return LIST_ACTION;
    }

    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @GetMapping("/assignShow/{id}")
    public String assignShow(@PathVariable("id") Long id,Model model){
        List<Map<String, Object>> zNodes = roleService.findPermissionByRoleId(id);
        model.addAttribute("zNodes", JSON.toJSONString(zNodes));
        model.addAttribute("roleId",id);
        return PAGE_ASSIGNSHOW;

    }
    @PreAuthorize("hasAnyAuthority('role.assgin')")
    @PostMapping("/assignPermission")
    public String assignPermission(@RequestParam("roleId") Long roleId,
                                   @RequestParam("permissionIds") List<Long> permissionIds,
                                   Model model
    ){
     roleService.saveRolePermission(roleId,permissionIds);
        return successPage(model,"设置角色权限成功");

    }

    @PreAuthorize("hasAnyAuthority('role.create')")
    @GetMapping("/create")
    public String create(){
        return PAGE_CREATE;
    }

}