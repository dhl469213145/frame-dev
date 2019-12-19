package com.framework.threadDemo;

import java.util.concurrent.atomic.AtomicStampedReference;

public class ABAResolveTest {

    private AtomicStampedReference<Integer> kReference = new AtomicStampedReference<>(10,0);

    private void transferNew() throws InterruptedException {
        System.out.println("开始转账（新系统：解决了ABA问题）");
        while(true) {
            Integer currentReference = kReference.getReference();
            int stamp = kReference.getStamp();
            System.out.println("由于CPU抢占问题，转账程序阻塞100ms");
            Thread.sleep(100);
            if (kReference.compareAndSet(currentReference,currentReference+1,stamp,stamp+1)){
                System.out.println("银行转账"+1+"元，成功。余额："+kReference.getReference());
                break;
            }
            System.err.println("警告：账户存在交易记录以外的资金流动");
        }
    }

    private void cleanMoneySub(){
        int stamp = kReference.getStamp();
        kReference.set(kReference.getReference()+2,stamp+1);
        System.out.println("非法组织洗钱，盗走2元，余额："+kReference.getStamp());
    }

    private void cleanMoneyAdd(){
        int stamp = kReference.getStamp();
        kReference.set(kReference.getReference()-2,stamp+1);
        System.out.println("非法组织洗钱，加入2元，余额："+kReference.getStamp());
    }

    private void newSystemTransfer() throws InterruptedException {
        ABAResolveTest abaResolveTest = new ABAResolveTest();
        System.out.println("账户余额："+abaResolveTest.kReference.getReference());

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    abaResolveTest.transferNew();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        Thread.sleep(20);
        abaResolveTest.cleanMoneyAdd();
        Thread.sleep(20);
        abaResolveTest.cleanMoneySub();

        Thread.sleep(200);
        System.out.println("银行卡原来余额为10，接收转账1元，故期望余额为11元。实际余额："+abaResolveTest.kReference.getReference());
    }

    public static void main(String[] args) throws InterruptedException {
        ABAResolveTest abaResolveTest = new ABAResolveTest();
        abaResolveTest.newSystemTransfer();
        /**
         * 运行结果：
         * 账户余额：10
         * 开始转账（新系统：解决了ABA问题）
         * 由于CPU抢占问题，转账程序阻塞100ms
         * 非法组织洗钱，加入2元，余额：1
         * 非法组织洗钱，盗走2元，余额：2
         * 警告：账户存在交易记录以外的资金流动
         * 由于CPU抢占问题，转账程序阻塞100ms
         * 银行转账1元，成功。余额：11
         * 银行卡原来余额为10，接收转账1元，故期望余额为11元。实际余额：11
         */
    }
}