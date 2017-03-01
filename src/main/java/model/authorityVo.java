package model;
/**   
 * @Title: authorityVo
 * @Package model 
 * @Description: authorityVo.java
 * @author Sdiver 18605916639_wo_cn   
 * @date 2017/3/1 上午2:37 
 * @version V1.0   
 */
public class authorityVo {
    private int authorityId;
    private int ifAthority;

    public int getAuthorityId() {
        return authorityId;
    }

    public void setAuthorityId(int authorityId) {
        this.authorityId = authorityId;
    }

    public int getIfAthority() {
        return ifAthority;
    }

    public void setIfAthority(int ifAthority) {
        this.ifAthority = ifAthority;
    }

    @Override
    public String toString() {
        return "authorityVo{" +
                "authorityId=" + authorityId +
                ", ifAthority=" + ifAthority +
                '}';
    }
}