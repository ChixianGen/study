package com.cxg.study.designpattern.template;   // Administrator 于 2019/8/28 创建;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TemplateDemo {

    public static void main(String[] args) {
        WorkDay designer = new Designer();
        designer.working();
        WorkDay programmer = new Programmer();
        programmer.working();
    }

    /**
     * 一个设计师的一天
     */
    static class Designer extends WorkDay {

        @Override
        protected void goOffWork() {
            log.debug("下班时间到。。。");
            shopping();
        }

        private void shopping() {
            log.debug("设计师去逛街购物了。。。");
        }

        @Override
        protected void specificWorking() {
            log.debug("设计师设计文案。。。");
        }

        @Override
        protected void arriveCompany() {
            log.debug("设计师开车到公司。。。");
        }
    }

    /**
     * 一个程序员的一天
     */
    static class Programmer extends WorkDay {
        @Override
        protected void goOffWork() {
            log.debug("下班时间到。。。");
            overtime();
        }

        private void overtime() {
            log.debug("程序员要加班了。。。");
        }

        @Override
        protected void specificWorking() {
            log.debug("程序员敲一天的代码。。。");
        }

        @Override
        protected void arriveCompany() {
            log.debug("程序员坐轻轨去公司。。。");
        }
    }

    /**
     * 抽象基类定义一个工作日的工作流程，
     * 让上班族具体实现各自的流程；
     */
    static abstract class WorkDay {
        public void working() {
            arriveCompany(); // 到达公司；
            specificWorking(); // 具体工作；
            goOffWork(); // 下班了；
        }

        protected abstract void goOffWork();

        protected void specificWorking() {
            log.debug("混水摸鱼一整天。。。");
        };

        protected abstract void arriveCompany();
    }
}