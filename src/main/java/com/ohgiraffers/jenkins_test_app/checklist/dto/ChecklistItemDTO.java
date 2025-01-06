package com.ohgiraffers.jenkins_test_app.checklist.dto;

public class ChecklistItemDTO {
    private Integer id;
    private Integer categoryId;
    private String name;
    private String description;
    private Boolean isChecked;

    public ChecklistItemDTO() {
    }

    public ChecklistItemDTO(Integer id, Integer categoryId, String name, String description, Boolean isChecked) {
        this.id = id;
        this.categoryId = categoryId;
        this.name = name;
        this.description = description;
        this.isChecked = isChecked;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    @Override
    public String toString() {
        return "ChecklistItemDTO{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isChecked=" + isChecked +
                '}';
    }

}
