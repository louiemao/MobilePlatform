package com.sws.platform.mobile.common.mqtt;

import com.alibaba.fastjson.JSONObject;
import org.fusesource.hawtbuf.Buffer;
import org.fusesource.hawtbuf.UTF8Buffer;
import org.fusesource.mqtt.client.Callback;
import org.fusesource.mqtt.client.CallbackConnection;
import org.fusesource.mqtt.client.Listener;
import org.fusesource.mqtt.client.MQTT;
import org.fusesource.mqtt.client.QoS;
import org.fusesource.mqtt.client.Tracer;
import org.fusesource.mqtt.codec.MQTTFrame;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

/**
 * Created by Louie on 2016/3/15.
 */
public class MqttClient {
    private Logger logger = LoggerFactory.getLogger(MqttClient.class);

    private String host = "tcp://localhost:61613";
    private String userName = "admin";
    private String passWord = "password";

    private MQTT mqtt;
    private CallbackConnection callbackConnection;
    private boolean isConnected = false;

    private static MqttClient instance = new MqttClient();

    public static MqttClient getInstance() {
        return instance;
    }

    private MqttClient() {
        try {
            mqtt = new MQTT();

            //MQTT设置说明
            mqtt.setHost(host);
            mqtt.setClientId("server"); //用于设置客户端会话的ID。在setCleanSession(false);被调用时，MQTT服务器利用该ID获得相应的会话。此ID应少于23个字符，默认根据本机地址、端口和时间自动生成
            mqtt.setCleanSession(true); //若设为false，MQTT服务器将持久化客户端会话的主体订阅和ACK位置，默认为true
            mqtt.setKeepAlive((short) 60);//定义客户端传来消息的最大时间间隔秒数，服务器可以据此判断与客户端的连接是否已经断开，从而避免TCP/IP超时的长时间等待
            mqtt.setUserName(userName);//服务器认证用户名
            mqtt.setPassword(passWord);//服务器认证密码

//            mqtt.setWillTopic("willTopic");//设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息
//            mqtt.setWillMessage("willMessage");//设置“遗嘱”消息的内容，默认是长度为零的消息
//            mqtt.setWillQos(QoS.AT_LEAST_ONCE);//设置“遗嘱”消息的QoS，默认为QoS.ATMOSTONCE
//            mqtt.setWillRetain(true);//若想要在发布“遗嘱”消息时拥有retain选项，则为true
//            mqtt.setVersion("3.1.1");
//
//            //失败重连接设置说明
//            mqtt.setConnectAttemptsMax(10L);//客户端首次连接到服务器时，连接的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1
//            mqtt.setReconnectAttemptsMax(3L);//客户端已经连接到服务器，但因某种原因连接断开时的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1
//            mqtt.setReconnectDelay(10L);//首次重连接间隔毫秒数，默认为10ms
//            mqtt.setReconnectDelayMax(30000L);//重连接间隔毫秒数，默认为30000ms
//            mqtt.setReconnectBackOffMultiplier(2);//设置重连接指数回归。设置为1则停用指数回归，默认为2
//
//            //Socket设置说明
//            mqtt.setReceiveBufferSize(65536);//设置socket接收缓冲区大小，默认为65536（64k）
//            mqtt.setSendBufferSize(65536);//设置socket发送缓冲区大小，默认为65536（64k）
//            mqtt.setTrafficClass(8);//设置发送数据包头的流量类型或服务类型字段，默认为8，意为吞吐量最大化传输
//
//            //带宽限制设置说明
//            mqtt.setMaxReadRate(0);//设置连接的最大接收速率，单位为bytes/s。默认为0，即无限制
//            mqtt.setMaxWriteRate(0);//设置连接的最大发送速率，单位为bytes/s。默认为0，即无限制
//
//            //选择消息分发队列
//            mqtt.setDispatchQueue(Dispatch.createQueue("foo"));//若没有调用方法setDispatchQueue，客户端将为连接新建一个队列。如果想实现多个连接使用公用的队列，显式地指定队列是一个非常方便的实现方法

            //设置跟踪器
            mqtt.setTracer(new Tracer() {
                @Override
                public void onReceive(MQTTFrame frame) {
                    logger.debug("recv: " + frame);
                }

                @Override
                public void onSend(MQTTFrame frame) {
                    logger.debug("send: " + frame);
                }

                @Override
                public void debug(String message, Object... args) {
                    logger.debug(String.format("debug: " + message, args));
                }
            });


            //使用回调式API
            callbackConnection = mqtt.callbackConnection();

            //连接监听
            callbackConnection.listener(new Listener() {

                //接收订阅话题发布的消息
                @Override
                public void onPublish(UTF8Buffer topic, Buffer payload, Runnable onComplete) {
                    logger.debug("======receive msg======" + payload.utf8().toString());
//                    onComplete.run();
                }

                //连接失败
                @Override
                public void onFailure(Throwable value) {
                    logger.debug("======connect failure======");
                    callbackConnection.disconnect(null);
                    isConnected = false;
                }

                //连接断开
                @Override
                public void onDisconnected() {
                    logger.debug("======mqtt disconnected======");
                    isConnected = false;
                }

                //连接成功
                @Override
                public void onConnected() {
                    logger.debug("======mqtt connected======");
                    isConnected = true;
                }
            });

            //连接
            callbackConnection.connect(new Callback<Void>() {
                //连接失败
                public void onFailure(Throwable value) {
                    logger.debug("======连接失败======" + value.getLocalizedMessage());
                    isConnected = false;
                }

                // 连接成功
                public void onSuccess(Void v) {
                    logger.debug("======连接成功======");
                    isConnected = true;
//                    //订阅主题
//                    Topic[] topics = {new Topic("foo", QoS.AT_LEAST_ONCE)};
//                    callbackConnection.subscribe(topics, new Callback<byte[]>() {
//                                //订阅主题成功
//                                public void onSuccess(byte[] qoses) {
//                                    logger.debug("========订阅成功=======");
//                                }
//
//                                //订阅主题失败
//                                public void onFailure(Throwable value) {
//                                    logger.debug("========订阅失败=======");
//                                    callbackConnection.disconnect(null);
//                                }
//                            }
//
//                    );
//
//                    //发布消息
//                    callbackConnection.publish("foo", ("Hello").getBytes("UTF-8"), QoS.AT_LEAST_ONCE, true, new Callback<Void>() {
//                        public void onSuccess(Void v) {
//                            logger.debug("===========消息发布成功============");
//                        }
//
//                        public void onFailure(Throwable value) {
//                            logger.debug("========消息发布失败=======");
//                            callbackConnection.disconnect(null);
//                        }
//                    });
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 向手机端推送消息
     *
     * @param topic       主题
     * @param title       通知栏标题
     * @param message     通知栏内容
     * @param packageName 要打开的应用的包名
     * @param className   要打开的应用的界面类名
     * @param content     要传给应用的内容
     */
    public void publish(String topic, String title, String message, String packageName, String className, String content) {
        try {
            if (!isConnected) {
                Thread.sleep(1000);
            }
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("title", title);
            jsonObject.put("text", message);
            if (packageName != null) {
                jsonObject.put("pkg", packageName);
            }
            if (className != null) {
                jsonObject.put("cls", className);
            }
            if (content != null) {
                jsonObject.put("content", content);
            }
            callbackConnection.publish(topic, jsonObject.toJSONString().getBytes("UTF-8"), QoS.AT_LEAST_ONCE, true, new Callback<Void>() {
                public void onSuccess(Void v) {
                    logger.debug("======消息发布成功======");
                }

                public void onFailure(Throwable value) {
                    logger.debug("======消息发布失败======" + value.getLocalizedMessage());
                    callbackConnection.disconnect(null);
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
