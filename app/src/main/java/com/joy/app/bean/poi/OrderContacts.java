package com.joy.app.bean.poi;


import com.android.library.utils.TextUtil;

/**
 * 折扣的购买人信息
 * 秒杀 报名个人信息
 *
 * @author xiaoyu.chen
 *         <p/>
 *         2014-7-23
 */
public class OrderContacts {

    private String contact_id = TextUtil.TEXT_EMPTY;
    private String name = TextUtil.TEXT_EMPTY;
    private String email = TextUtil.TEXT_EMPTY;
    private String phone = TextUtil.TEXT_EMPTY;

    public String getContact_id() {
        return contact_id;
    }

    public void setContact_id(String contact_id) {
        this.contact_id = contact_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * 联系人信息是否全为空 只包含name phone email
     *
     * @return
     */
    public boolean isEmpty() {

        return TextUtil.isEmptyTrim(name) && TextUtil.isEmptyTrim(phone) && TextUtil.isEmptyTrim(email);
    }

    @Override
    public String toString() {
        return "OrderContacts{" +
                "contact_id='" + contact_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
