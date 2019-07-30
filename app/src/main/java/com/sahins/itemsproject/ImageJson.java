package com.sahins.itemsproject;

import java.util.List;

public class ImageJson {

    private List<İtemsBean> items;

    public List<İtemsBean> getİtems() {
        return items;
    }

    public void setİtems(List<İtemsBean> items) {
        this.items = items;
    }

    public static class İtemsBean {
        /**
         * title : Robert MacArthur Crawford
         * url : image-MPK9uyivbMPMjcwVW-rCJA.jpg
         */

        private String title;
        private String url;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
