package Vazifa64.Entity;

public class Company {
    private int companyId;
    private int userId;
    private String companyName;
    private String information;
    private String webSaytLink;
    private String mainOfficeLocation;
    private int numberOfWorker;
    private String owner;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getCompanyId() {
        return companyId;
    }

    public void setCompanyId(int companyId) {
        this.companyId = companyId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getWebSaytLink() {
        return webSaytLink;
    }

    public void setWebSaytLink(String webSaytLink) {
        this.webSaytLink = webSaytLink;
    }

    public String getMainOfficeLocation() {
        return mainOfficeLocation;
    }

    public void setMainOfficeLocation(String mainOfficeLocation) {
        this.mainOfficeLocation = mainOfficeLocation;
    }

    public int getNumberOfWorker() {
        return numberOfWorker;
    }

    public void setNumberOfWorker(int numberOfWorker) {
        this.numberOfWorker = numberOfWorker;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
