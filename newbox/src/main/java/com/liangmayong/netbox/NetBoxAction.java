package com.liangmayong.netbox;

import com.liangmayong.netbox.defualts.DefualtNetBoxConverter;
import com.liangmayong.netbox.defualts.DefualtNetBoxInterceptor;
import com.liangmayong.netbox.interfaces.NetBoxConverter;
import com.liangmayong.netbox.interfaces.NetBoxInterceptor;

/**
 * Created by liangmayong on 2016/9/12.
 */
public abstract class NetBoxAction {

    // interceptorTypes
    private Class<? extends NetBoxInterceptor> interceptorType = DefualtNetBoxInterceptor.class;
    // converterTypes
    private Class<? extends NetBoxConverter> converterType = DefualtNetBoxConverter.class;

    /**
     * getConverter
     *
     * @return converter
     */
    public final NetBoxConverter getConverter() {
        return NetBox.getConverterInstance(converterType);
    }

    /**
     * getInterceptor
     *
     * @return interceptor
     */
    public final NetBoxInterceptor getInterceptor() {
        return NetBox.getInterceptorInstance(interceptorType);
    }

    /**
     * setConverter
     *
     * @param converterType converterType
     */
    public final void setConverter(Class<? extends NetBoxConverter> converterType) {
        if (converterType == null) {
            converterType = DefualtNetBoxConverter.class;
        }
        this.converterType = converterType;
    }

    /**
     * setInterceptor
     *
     * @param interceptorType interceptorType
     */
    public final void setInterceptor(Class<? extends NetBoxInterceptor> interceptorType) {
        if (interceptorType == null) {
            interceptorType = DefualtNetBoxInterceptor.class;
        }
        this.interceptorType = interceptorType;
    }

    /**
     * getBaseUrl
     *
     * @return base url
     */
    public abstract String getBaseUrl();

}
