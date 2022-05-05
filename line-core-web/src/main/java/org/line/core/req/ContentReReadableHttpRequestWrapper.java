package org.line.core.req;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
* @Description: 请求体可多次读取的HttpRequest请求包装类, 解决request中的数据只能读取一次的问题
*/
public class ContentReReadableHttpRequestWrapper extends HttpServletRequestWrapper {

    private ServletInputStream inputStream;

    /**
     * Create a new ContentReReadableHttpRequestWrapper for the given servlet request.
     * @param request the original servlet request
     */
    public ContentReReadableHttpRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    /**
     * 对inputStream进行包装
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        if (this.inputStream == null) {
            this.inputStream = new ContentReReadableHttpRequestWrapper.ContentReReadableInputStream(getRequest().getInputStream());
        }
        return this.inputStream;
    }

    /**
     * 将读取过的内容重新写入到流内
     */
    public void reWriteContentIntoInputStream(byte[] content) throws IOException {
        ((ContentReReadableHttpRequestWrapper.ContentReReadableInputStream)getInputStream()).setIs(new ByteArrayInputStream(content));
    }

    /**
     * 可以重复设置InputStream流的输入流实现
     */
    private class ContentReReadableInputStream extends ServletInputStream {

        private InputStream is;

        public ContentReReadableInputStream(InputStream is) {
            this.is = is;
        }

        public void setIs(InputStream is) {
            this.is = is;
        }

        @Override
        public int read() throws IOException {
            return is.read();
        }

        public boolean isFinished() {
            return false;
        }

        public boolean isReady() {
            return false;
        }

        public void setReadListener(ReadListener listener) {

        }
    }

}
