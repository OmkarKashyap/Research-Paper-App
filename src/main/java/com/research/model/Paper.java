// package com.research.model;

// import jakarta.persistence.*;
// import lombok.Data;
// import java.util.Date;
// import java.util.List;

// @Data
// @Entity
// @Table(name = "papers")
// public class Paper {
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Integer paperId;

//     private String title;
//     private String abstract_;
//     private String content;
//     private Date submissionDate;

//     @ManyToOne
//     @JoinColumn(name = "author_id")
//     private Author author;

//     @OneToMany(mappedBy = "paper", cascade = CascadeType.ALL)
//     private List<Comment> comments;
// }

package com.research.model;

import jakarta.persistence.*; // Or import javax.persistence.* if using an older version
import java.util.List;

@Entity
@Table(name = "papers")
public class Paper {
    
    @Id
    @Column(name = "paper_id")
    private String id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "abstract_text", columnDefinition = "TEXT")
    private String abstractText;
    
    @Column(name = "url")
    private String url;
    
    @Column(name = "venue")
    private String venue;
    
    @Column(name = "publication_date")
    private String publicationDate;
    
    @Column(name = "citation_count")
    private int citationCount;
    
    @Column(name = "pdf_url")
    private String pdfUrl;
    
    @Column(name = "authors")
    private String authors; // Storing as a comma-separated string for simplicity

    // **Constructors**
    public Paper() {}

    public Paper(String id, String title, String abstractText, String url, String venue, String publicationDate,
                 int citationCount, String pdfUrl, String authors) {
        this.id = id;
        this.title = title;
        this.abstractText = abstractText;
        this.url = url;
        this.venue = venue;
        this.publicationDate = publicationDate;
        this.citationCount = citationCount;
        this.pdfUrl = pdfUrl;
        this.authors = authors;
    }

    // **Getters and Setters**
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAbstractText() { return abstractText; }
    public void setAbstractText(String abstractText) { this.abstractText = abstractText; }

    public String getUrl() { return url; }
    public void setUrl(String url) { this.url = url; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getPublicationDate() { return publicationDate; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }

    public int getCitationCount() { return citationCount; }
    public void setCitationCount(int citationCount) { this.citationCount = citationCount; }

    public String getPdfUrl() { return pdfUrl; }
    public void setPdfUrl(String pdfUrl) { this.pdfUrl = pdfUrl; }

    public String getAuthors() { return authors; }
    public void setAuthors(String authors) { this.authors = authors; }
}
