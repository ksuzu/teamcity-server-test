public class Build {
    private String buildTypeId;
    private Long id;
    private String status;

    public Build(String buildTypeId) {
        this.buildTypeId = buildTypeId;
    }

    public Build(Long id) {
        this.id = id;
    }

    public Build() {
    }

    public String getBuildTypeId() {
        return buildTypeId;
    }

    public void setBuildTypeId(String buildTypeId) {
        this.buildTypeId = buildTypeId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getStatus() {
        return buildTypeId;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
