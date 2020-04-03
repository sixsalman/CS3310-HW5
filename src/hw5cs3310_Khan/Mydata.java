package hw5cs3310_Khan;

/**
 * Instances of this class contain a student's name, course no. and grade
 */
class Mydata implements Comparable {

    private String stuName;
    private int courseNumber;
    private char grade;

    //... // properties, constructors and methods

    /**
     * Creates an instance of this class storing the received parameters
     * @param stuName receives name of the student
     * @param courseNumber receives a course number
     * @param grade receives the student's grade for the course
     */
    Mydata(String stuName, int courseNumber, char grade) {
        String[] nameTokens = stuName.split(":");
        if (nameTokens.length == 1) {
            stuName = nameTokens[0];
        } else {
            if (nameTokens[0].length() == 0) {
                stuName = nameTokens[1];
            } else {
                stuName = nameTokens[0] + " " + nameTokens[1];
            }

        }
        this.stuName = stuName;
        this.courseNumber = courseNumber;
        this.grade = grade;
    }

    /**
     * Gets student's name
     * @return a string containing student's name
     */
    String getStuName() {
        return stuName;
    }

    /**
     * Returns contents stored in the calling instance of this class
     * @return a string containing fields of the calling instance
     */
    @Override
    public String toString() {
        String[] nameTokens = stuName.split(":");
        String name;
        if (nameTokens.length == 1) {
            name = nameTokens[0];
        } else {
            name = nameTokens[0] + " " + nameTokens[1];

        }

        String cNum = "";
        if (courseNumber < 1000 && courseNumber >= 0)
            cNum += "0".repeat(4 - String.valueOf(courseNumber).length());
        cNum += courseNumber;

        return String.format("\t\tName: %-18s\tCourse Number: %-4s\t\tGrade: %c", name, cNum, grade);
    }

    /**
     * Compares name of the calling instance with that of the received instance
     * @param o receives the instance to compare
     * @return a negative value if 'o' is greater, 0 if both are equal or a positive value if the calling instance is
     * greater
     */
    @Override
    public int compareTo(Object o) {
        Mydata other = (Mydata) o;
        return stuName.compareTo(other.stuName);
    }

}