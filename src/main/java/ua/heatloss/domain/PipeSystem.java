package ua.heatloss.domain;

public enum PipeSystem {

    VERTICAL_SINGLE("Vertical", 1),
    VERTICAL_DOUBLE("Vertical", 2),
    HORIZONTAL_DOUBLE("Horizontal", 1),
    HORIZONTAL_SINGLE("Horizontal", 1);


    private String structure;
    private int amountOfPipes;

    PipeSystem(String structure, int amountOfPipes) {
        this.amountOfPipes = amountOfPipes;
        this.structure = structure;
    }

    public String getStructure() {
        return structure;
    }

    public void setStructure(String structure) {
        this.structure = structure;
    }

    public int getAmountOfPipes() {
        return amountOfPipes;
    }

    public void setAmountOfPipes(int amountOfPipes) {
        this.amountOfPipes = amountOfPipes;
    }
}
