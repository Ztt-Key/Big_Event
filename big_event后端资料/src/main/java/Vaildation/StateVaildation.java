package Vaildation;

import com.ze.Anno.Stste;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * ConstraintValidator<给那个注解提供校验规则,校验的数据类型>
 */
public class StateVaildation implements ConstraintValidator<Stste,String> {
    /**
     *
     * @param s  将来要校验的数据
     * @param Context
     * @return 如果返回false校验不通过
     */

    @Override
    public boolean isValid(String s, ConstraintValidatorContext Context) {
        //提供校验规则
        if (s==null){
            return false;
        }
        if (s.equals("已发布")||s.equals("草稿")){
            return true;
        }
        return false;
    }
}
