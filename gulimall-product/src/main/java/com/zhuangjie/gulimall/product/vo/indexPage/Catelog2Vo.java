package com.zhuangjie.gulimall.product.vo.indexPage;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Catelog2Vo implements Serializable {
    private static final long serialVersionUID = 1L;

    /*
   catalog1Id
   catalog3List
   id
   name
    */
    private Long catalog1Id;
    private Long id;
    private String name;
    private List<Catelog3Vo> catalog3List;

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    public static class Catelog3Vo implements Serializable {
        private static final long serialVersionUID = 1L;

        private Long catalog2Id;
        private Long id;
        private String name;
    }
}
