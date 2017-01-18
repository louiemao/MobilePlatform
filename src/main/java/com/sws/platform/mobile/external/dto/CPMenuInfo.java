package com.sws.platform.mobile.external.dto;

import com.sws.platform.mobile.security.dto.Menu;

/**
 * Created by Louie on 2015/8/12.
 */
public class CPMenuInfo {
    public String MenuCd;
    public String MenuDesc;
    public String MenuId;
    //所属程序集
    public String RelatedAsm;
    //所属命名空间
    public String RelatedNS;
    public int SortId;
    public String SuperMenuId;

    public Menu convertToMenuInfo(){
        Menu info=new Menu();
        info.setId(this.MenuId);
        info.setLabel(this.MenuDesc);
        info.setLink(this.RelatedAsm);
        info.setSort(this.SortId);
        info.setParentId(this.SuperMenuId);
        return info;
    }
}
