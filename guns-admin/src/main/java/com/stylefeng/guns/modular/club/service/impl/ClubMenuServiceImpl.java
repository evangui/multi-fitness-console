package com.stylefeng.guns.modular.club.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.stylefeng.guns.core.node.MenuNode;
import com.stylefeng.guns.core.node.ZTreeNode;
import com.stylefeng.guns.modular.club.service.IClubMenuService;
import com.stylefeng.guns.modular.system.dao.ClubMenuMapper;
import com.stylefeng.guns.modular.system.model.ClubMenu;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 菜单服务
 *
 * @author guiyj007
 * @date 2017-05-05 22:20
 */
@Service
public class ClubMenuServiceImpl extends ServiceImpl<ClubMenuMapper, ClubMenu> implements IClubMenuService {

    @Resource
    private ClubMenuMapper clubMenuMapper;

    @Override
    public void delMenu(Long menuId) {

        //删除菜单
        this.clubMenuMapper.deleteById(menuId);

        //删除关联的relation
        this.clubMenuMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public void delMenuContainSubMenus(Long menuId) {

        ClubMenu menu = clubMenuMapper.selectById(menuId);

        //删除当前菜单
        delMenu(menuId);

        //删除所有子菜单
        Wrapper<ClubMenu> wrapper = new EntityWrapper<>();
        wrapper = wrapper.like("pcodes", "%[" + menu.getCode() + "]%");
        List<ClubMenu> menus = clubMenuMapper.selectList(wrapper);
        for (ClubMenu temp : menus) {
            delMenu(temp.getId());
        }
    }

    @Override
    public List<Map<String, Object>> selectMenus(String condition, String level) {
        return this.baseMapper.selectMenus(condition, level);
    }

    @Override
    public List<Long> getMenuIdsByRoleId(Integer roleId) {
        return this.baseMapper.getMenuIdsByRoleId(roleId);
    }

    @Override
    public List<ZTreeNode> menuTreeList() {
        return this.baseMapper.menuTreeList();
    }

    @Override
    public List<ZTreeNode> menuTreeListByMenuIds(List<Long> menuIds) {
        return this.baseMapper.menuTreeListByMenuIds(menuIds);
    }

    @Override
    public int deleteRelationByMenu(Long menuId) {
        return this.baseMapper.deleteRelationByMenu(menuId);
    }

    @Override
    public List<String> getResUrlsByRoleId(Integer roleId) {
        return this.baseMapper.getResUrlsByRoleId(roleId);
    }

    @Override
    public List<MenuNode> getMenusByRoleIds(List<Integer> roleIds) {
        return this.baseMapper.getMenusByRoleIds(roleIds);
    }
}
