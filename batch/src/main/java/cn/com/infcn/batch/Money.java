package cn.com.infcn.batch;

import java.util.List;

public class Money {
    
    private Double money;
    private boolean iscontainsKey;
    private boolean isequal;//全词匹配
    private boolean isjlr;//是否净利润关键词
    
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public boolean isIsequal() {
        return isequal;
    }
    public void setIsequal(boolean isequal) {
        this.isequal = isequal;
    }
    public boolean isIscontainsKey() {
        return iscontainsKey;
    }
    public void setIscontainsKey(boolean iscontainsKey) {
        this.iscontainsKey = iscontainsKey;
    }
    public boolean isIsjlr() {
        return isjlr;
    }
    public void setIsjlr(boolean isjlr) {
        this.isjlr = isjlr;
    }
    
    public static void main(String[] args) {
        List<String> l = null;
        try {
            System.out.println(l.size());
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(2);
    }
    
}
