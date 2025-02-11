package kr.kookmin.jeongo3.Post;

public enum PostType {

    QNA("지식인"), FREE("자유");

    private String postName;

    PostType(String postName) {
        this.postName = postName;
    }
}
