/**
 * 文  件   名：EnDecryptionBase.java
 * 版        权：厦门歪坏信息科技有限公司
 * 创  建  者：Derek
 * 时       间：2013-10-8 , 下午3:54:23
 * 文件描述：<描述>
 * ***********************修改记录***********************
 * 修  改  者：[可选]
 * 修  改  点：[可选]
 * 修改描述：[可选]
 * ******************************************************
 */
package com.xiayule.commonlibrary.endecryption;

/**
 * 功能详细描述
 * @author Derek
 * @version 版本号，2013-10-8
 * @see [相关类/方法]
 * @since [产品/模块版本，从哪个版本开始有该类型]
 */
public abstract class EnDecryptionBase implements IEnDecryption {

    String key = "";

    /**
     * @param key
     */
    public EnDecryptionBase(String key) {
        super();
        this.key = key;
    }

    /* (non-Javadoc)
     * @see com.wifibanlv.www.util.endecryption.IEnDecryption#decrypt(java.lang.String)
     */
    @Override
    public String decrypt(String encryptData) {
        // TODO Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     * @see com.wifibanlv.www.util.endecryption.IEnDecryption#encrypt(java.lang.String)
     */
    @Override
    public String encrypt(String data) {
        // TODO Auto-generated method stub
        return null;
    }

}
