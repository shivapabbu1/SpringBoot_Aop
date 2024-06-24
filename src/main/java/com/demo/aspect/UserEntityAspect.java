package com.demo.aspect;

import com.demo.entity.UserEntity;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class UserEntityAspect {

    @DeclareParents(value = "com.demo.entity.UserEntity+", defaultImpl = UserEntityMixin.class)
    public static DetailedDescription detailedDescription;

    public interface DetailedDescription {
        String getDetailedDescription();
    }

    public static class UserEntityMixin implements DetailedDescription {
        private UserEntity userEntity;

        // Constructor to accept the UserEntity instance
        public UserEntityMixin(UserEntity userEntity) {
            this.userEntity = userEntity;
        }

        @Override
        public String getDetailedDescription() {
            return userEntity.getName() + ": " + userEntity.getDescription();
        }
    }
}
