package spring.study.server.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Request<T> {

    private Header header;
    private T httpBody;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Header {

        private String responseCode;

        @Override
        public String toString() {
            return "Header{" +
                "responseCode='" + responseCode + '\'' +
                '}';
        }
    }

    @Override
    public String toString() {
        return "Request{" +
            "header=" + header +
            ", httpBody=" + httpBody +
            '}';
    }
}
