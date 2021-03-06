package my.examples.pouch.dto.custom;

import lombok.Data;

import java.util.*;

@Data
public class CustomLink {
    private Long id;
    private String title;
    private String tagName;
    private String url;
    private String src;
    private String content;
    private Date regDate;
    Set<CustomTag> tags;
    List<CustomTagDto> tagDtos;
    public CustomLink(){
        tagDtos = new ArrayList<>();
        tags = new HashSet<>();
    }
}
