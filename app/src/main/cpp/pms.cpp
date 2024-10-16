#include <iostream>
#include <jni.h>
#include "HTTPRequest.hpp"
#define BACK_URL "http://192.168.1.43:8080/api/user/"

extern "C" {
void encrypt(std::string &str) {
    // Traverse the string
    for (int i = 0; i < str.length(); i++)
        if (str[i] + 1 != '\0' && str[i] + 1 != '\n' && str[i] + 1 != '\b')
            str[i] = str[i] + 1;
}
void uncrypt(std::string &str) {
    // Traverse the string
    for (int i = 0; i < str.length(); i++)
        if (str[i] - 1 != '\0' && str[i] - 1 != '\n' && str[i] - 1 != '\b')
            str[i] = str[i] - 1;
}

int nthOccurrence(const std::string& str, const char findMe, int nth)
{
    size_t  pos = 0;
    int     cnt = 0;

    while( cnt != nth )
    {
        pos+=1;
        pos = str.find(findMe, pos);
        if ( pos == std::string::npos )
            return -1;
        cnt++;
    }
    return pos;
}

std::string jstring2string(JNIEnv *env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray) env->CallObjectMethod(jStr, getBytes,
                                                                       env->NewStringUTF("UTF-8"));

    size_t length = (size_t) env->GetArrayLength(stringJbytes);
    jbyte *pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char *) pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}

jstring jniLogin(JNIEnv *env, jobject thiz, jstring j_name, jstring j_password, jstring j_email) {

    try {
        std::string name = jstring2string(env, j_name);
        std::string password = jstring2string(env, j_password);
        std::string email = jstring2string(env, j_email);
        // you can pass http::InternetProtocol::V6 to Request to make an IPv6 request
        http::Request get_request{BACK_URL + name};
        // send a get request
        const auto get_response =  get_request.send("GET");
        switch (get_response.status.code) {
            case 200 : {
                std::string body =  std::string(get_response.body.begin(), get_response.body.end());
                std::string enc_pw = body.substr(
                            nthOccurrence(body, '"', 7),
                            (nthOccurrence(body, '"', 8) - nthOccurrence(body, '"', 7))
                        );
//                return jstring(enc_pw.c_str());
                uncrypt(enc_pw);
                if (password == enc_pw){
                    return env->NewStringUTF("SUCCESS");
                }else{
                    return env->NewStringUTF("FAILED");
                }
                break;
            }
            default: {
                return env->NewStringUTF("Nope");
                break;
            }
        }

    }
    catch (const std::exception &e) {
        std::cerr << "Request failed, error: " << e.what() << '\n';
        return env->NewStringUTF(e.what());
    }
}

jstring jniRegister(JNIEnv *env, jobject thiz, jstring j_name, jstring j_password, jstring j_email) {
    try {
        std::string name = jstring2string(env, j_name);
        std::string password = jstring2string(env, j_password);
        std::string email = jstring2string(env, j_email);
        // you can pass http::InternetProtocol::V6 to Request to make an IPv6 request
        http::Request get_request{BACK_URL + name};
        // send a get request
        const auto get_response =  get_request.send("GET");
        switch (get_response.status.code) {
            case 200 : {
                return env->NewStringUTF("USERNAME TAKEN");
                break;
            }
            case 404 : {
                encrypt(password);
                http::Request post_request{BACK_URL};
                std::string body =
                        "{\n\"name\": \"" + name + "\",\n\"password\":\"" + password + "\"";
                if (!email.empty()) {
                    body = body + ",\n\"email\":\"" + email + "\"";
                }
                body = body + std::string("\n}");
                return env->NewStringUTF(body.c_str());
                //const auto post_response = post_request.send("POST");
                return env->NewStringUTF("SUCCESS");
                break;
            }
            default: {
                return env->NewStringUTF("THIS SHOULDNT BE HERE");
                break;
            }
        }

    }
    catch (const std::exception &e) {
        std::cerr << "Request failed, error: " << e.what() << '\n';
        return env->NewStringUTF(e.what());
    }
}

JNIEXPORT jstring JNICALL
Java_com_example_pms_Register_jniRegister(JNIEnv *env, jobject thiz, jstring j_name, jstring j_password, jstring j_email) {
    return (jstring) jniRegister(env, thiz, j_name, j_password, j_email);
}
JNIEXPORT jstring JNICALL
Java_com_example_pms_Login_jniLogin(JNIEnv *env, jobject thiz, jstring j_name, jstring j_password, jstring j_email) {
    return (jstring) jniLogin(env, thiz, j_name, j_password, j_email);
}
};