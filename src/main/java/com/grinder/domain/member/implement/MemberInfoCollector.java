package com.grinder.domain.member.implement;

import com.grinder.domain.member.model.login.DeviceType;
import com.grinder.domain.member.model.login.MemberConnectionInfo;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j

public class MemberInfoCollector {
    private final HttpServletRequest request;

    public MemberConnectionInfo connectionInfo(){
        return MemberConnectionInfo.builder()
                .ipAddress(getMemberIp())
                .deviceInfo(getDeviceInfo())
                .connectionTime(LocalDateTime.now())
                .build();
    }

    /**
     * 추후 프록시 서버,aws 배포 고려 및 로컬 환경에서의 헤더 순차적으로 탐색
     * IPv6 루프백 주소 IPv4 로 변환.
     */
    private String getMemberIp() {
        String[] headers = {
                "X-Forwarded-For",
                "Proxy-Client-IP",
                "WL-Proxy-Client-IP",
                "HTTP_X_FORWARDED_FOR",
                "HTTP_X_FORWARDED",
                "HTTP_X_CLUSTER_CLIENT_IP",
                "HTTP_CLIENT_IP",
                "HTTP_FORWARDED_FOR",
                "HTTP_FORWARDED",
                "X-Real-IP"
        };

        String ip = null;

        for (String header : headers) {
            ip = request.getHeader(header);
            if (isValidIP(ip)) {
                int idx = ip.indexOf(',');
                if (idx > -1) {
                    ip = ip.substring(0, idx).trim();
                }
                break;
            }
        }

        if (!isValidIP(ip)) {
            ip = request.getRemoteAddr();
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                try {
                    InetAddress inetAddress = InetAddress.getLocalHost();
                    ip = inetAddress.getHostAddress();
                } catch (UnknownHostException e) {
                    ip = "127.0.0.1";
                }
            }
        }

        return ip;
    }

    /**
     *IPv4형식 검증 메서드
     */
    private boolean isValidIP(String ip) {
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            return false;
        }
        String ipPattern = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
                + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

        return ip.matches(ipPattern);
    }

    private String getDeviceInfo() {
        String userAgent = request.getHeader("User-Agent");
        DeviceType deviceType;

        if (userAgent.toLowerCase().contains("mobile")) {
            deviceType = DeviceType.MOBILE;
        } else {
            deviceType = DeviceType.PC;
        }
        return deviceType.name() + " - " + userAgent;
    }
}
