/* 
 * Copyright (C), 2014-2016, 时代际客(深圳)软件有限公司
 * File Name: @(#)MarshallingCodecFactory.java
 * Encoding UTF-8
 * Author: zl
 * Version: 3.0
 * Date: 2017年9月23日
 */
package org.demo.netty.ch12;

import java.io.IOException;

import org.jboss.marshalling.Marshaller;
import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;
import org.jboss.marshalling.Unmarshaller;

/** 
 * 功能描述
 * 
 * <p>
 * <a href="MarshallingCodecFactory.java"><i>View Source</i></a>
 * </p>
 * @author zl
 * @version 3.0
 * @since 1.0 
*/
public final class MarshallingCodecFactory {
    
    /**
     * 创建Jboss Marshaller
     * @return
     * @throws IOException
    */
    protected static Marshaller buildMarshalling() throws IOException {
        MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
        MarshallingConfiguration config = new MarshallingConfiguration();
        config.setVersion(5);
        Marshaller marshaller = factory.createMarshaller(config);
        return marshaller;
    }
    
    /**
     * 创建Jboss Unmarshaller
     * @return
     * @throws IOException
    */
    protected static Unmarshaller buildUnMarshalling() {
        try {

            MarshallerFactory factory = Marshalling.getProvidedMarshallerFactory("serial");
            MarshallingConfiguration config = new MarshallingConfiguration();
            config.setVersion(5);
            Unmarshaller unmarshaller = factory.createUnmarshaller(config);
            return unmarshaller;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
