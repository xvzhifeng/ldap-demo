package sun;

import sun.entity.Person;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.Control;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Hashtable;

/**
 * @author sumu
 * @date 12/31/2021 3:52 PM
 */
public class LdapOperation {
    private String ldapUrl;
    private String dn;
    private String password;
    public LdapContext ldapContext;
    public Control[] controls = null;

    public LdapOperation(String ldapUrl, String dn, String password) {
        this.ldapUrl = ldapUrl;
        this.dn = dn;
        this.password = password;
    }

    public boolean login() {
        Hashtable<String, Object> env = new Hashtable<String, Object>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, this.ldapUrl + this.dn);
        env.put(Context.SECURITY_PRINCIPAL, this.dn);
        env.put(Context.SECURITY_CREDENTIALS, this.password);
        try {
            ldapContext = new InitialLdapContext(env, controls);
            System.out.println("连接成功");
            return true;
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return false;
    }

    public Person search(String key) {
        String personDn = "cn=" + key + "," + dn;
        Attributes matchAttrs = new BasicAttributes(true);
        matchAttrs.put(new BasicAttribute("username", key));
        Person person1 = new Person();
// Search for objects that have those matching attributes
        try {

            NamingEnumeration<SearchResult> answer = ldapContext.search(dn, matchAttrs);
            while (answer.hasMore()) {
                SearchResult sr = answer.next();
                Attributes attributes = sr.getAttributes();
                person1.setUsername(String.valueOf(attributes.get("username").get(0)));
                person1.setPassword(String.valueOf(attributes.get("password").get(0)));
                person1.setUserEmail(String.valueOf(attributes.get("email").get(0)));
                person1.setPhone(String.valueOf(attributes.get("phone").get(0)));
                person1.setAge(String.valueOf(attributes.get("age").get(0)));

            }
        } catch (NamingException e) {
            e.printStackTrace();
        }

        return person1;
    }

    public boolean addPerson(Person person) {
        Attributes attrs = new BasicAttributes();
        attrs.put("username", person.getUsername());
        attrs.put("password", person.getPassword());
        attrs.put("age", person.getAge());
        attrs.put("email", person.getUserEmail());
        attrs.put("phone", person.getPhone());

        Attribute objClass = new BasicAttribute("objectClass");
        objClass.add("top");
        objClass.add("organizationalRole");
        objClass.add("userPerson");
        attrs.put(objClass);
        String personDn = "cn=" + person.getUsername() + "," + dn;
        try {
            ldapContext.createSubcontext(personDn, attrs);
            System.out.println(person.getUsername() + "添加成功");
            return true;
        } catch (NamingException e) {
            System.out.println("添加失败");
            e.printStackTrace();
        }
        return false;
    }

    public boolean updatePerson(Person person) {
        String personDn = "cn=" + person.getUsername() + "," + dn;
        ModificationItem[] mods = new ModificationItem[5];
        Attribute attr = new BasicAttribute("username", person.getUsername());
        mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("username", person.getUsername()));
        mods[1] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("password", person.getPassword()));
        mods[2] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("age", person.getAge()));
        mods[3] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("email", person.getUserEmail()));
        mods[4] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("phone", person.getPhone()));
        try {
            ldapContext.modifyAttributes(personDn,mods);
            System.out.println("更新成功");
            return true;
        } catch (NamingException e) {
            System.out.println("更新失败");
            e.printStackTrace();
        }
        return false;
    }

    public boolean deletePerson(String username) {
        String personDn = "cn=" + username + "," + dn;
        try {
            ldapContext.destroySubcontext(personDn);
            System.out.println(username + "删除成功");
            return true;
        } catch (NamingException e) {
            System.out.println(username + "删除失败");
            e.printStackTrace();
        }
        return false;
    }

    /**
     *  模拟用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return
     */
    public boolean userLogin(String username, String password) {
        Person person = search(username);
        System.out.println(person);
        if(person!=null && person.getUsername().equals(username) && person.getPassword().equals(password)) {
            System.out.println("登录成功");
            return true;
        } else {
            System.out.println("登录失败");
            return false;
        }
    }



}
