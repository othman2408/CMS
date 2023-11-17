package cms.Entity;

import java.util.List;

public class Paper {
    private int paperId;
    private String title;
    private List<Author> authors;
    private List<Reviewer> reviewers;
    private List<String> keywords;
    private String Abstract;
    private String status;

    public Paper() {
    }

    public Paper( String title, List<Author> authors, List<Reviewer> reviewers, List<String> keywords, String anAbstract, String status) {
        this.title = title;
        this.authors = authors;
        this.reviewers = reviewers;
        this.keywords = keywords;
        Abstract = anAbstract;
        this.status = status;
    }

    public int getPaperId() {
        return paperId;
    }

    public void setPaperId(int paperId) {
        this.paperId = paperId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public List<Reviewer> getReviewers() {
        return reviewers;
    }

    public void setReviewers(List<Reviewer> reviewers) {
        this.reviewers = reviewers;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getAbstract() {
        return Abstract;
    }

    public void setAbstract(String anAbstract) {
        Abstract = anAbstract;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Paper{" +
                "paperId=" + paperId +
                ", title='" + title + '\'' +
                ", authors=" + authors +
                ", reviewers=" + reviewers +
                ", keywords=" + keywords +
                ", Abstract='" + Abstract + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
