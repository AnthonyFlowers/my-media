package mymedia.domain;

public enum MovieQueryParam {
    PAGE("page"),
    PAGE_SIZE("pageSize");

    private final String urlParam;

    MovieQueryParam(String urlParam) {
        this.urlParam = urlParam;
    }

    public String getUrlParam() {
        return urlParam;
    }
}
