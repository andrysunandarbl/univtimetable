package ua.kiev.univ.timetable;

import java.io.Serializable;

import org.jgap.*;
import org.jgap.impl.*;

import java.util.StringTokenizer;

public class GroupGene extends IntegerGene implements Gene, Serializable {
  private static final String TOKEN_SEPARATOR = ":";
  
  private Integer groupNumber;
  private Integer groupSize;
  private Integer idGroup;
  private String name;
  
  private static Integer[] allGroupSize = new Integer[Start.MAX_NUMBER_OF_GROUPS];
  private static Integer[] allIdGroup = new Integer[Start.MAX_NUMBER_OF_GROUPS];
  private static String[] allNames = new String[Start.MAX_NUMBER_OF_GROUPS];
  private static Integer max_idGroup = Start.MAX_NUMBER_OF_GROUPS;
 
  // study_plan[idGroup][idLesson] = how many times this lesson should take place
  private static Integer[][] study_plan = new Integer[20][20];
  //TODO: remove this magic numbers into constants

  //In the inputGroupSize we store the reference data about groupSize in the
  // GroupGene with particular idGroup


  public GroupGene(Configuration a_conf,
                   Integer a_GroupNumber) throws InvalidConfigurationException {
    super(a_conf);
    //TODO: implement XML validation
    try {
      groupNumber = a_GroupNumber;
      idGroup = allIdGroup[groupNumber];
      name = allNames[groupNumber];
      groupSize = allGroupSize[groupNumber];
    } catch (Exception e) {
      throw new InvalidConfigurationException(e.getMessage());
    }

  }


  @Override
  public Gene newGeneInternal() {
    try {
      return new GroupGene(getConfiguration(), max_idGroup-1);
    } catch (InvalidConfigurationException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }

  public void setAllele(Object a_idGroup) {
    groupNumber = (Integer)a_idGroup;
    idGroup = allIdGroup[groupNumber];
    name = allNames[groupNumber];
    groupSize = allGroupSize[groupNumber];
  }


  public Object getAllele() {
    return groupNumber;
  }

  @Override
  public void setToRandomValue(RandomGenerator a_randomGenerator) {
    groupNumber = new Integer(a_randomGenerator.nextInt(max_idGroup));
    idGroup = allIdGroup[groupNumber];
    name = allNames[groupNumber];
    groupSize = allGroupSize[groupNumber];
  }


  @Override
  public String getPersistentRepresentation()
    /* throws UnsupportedRepresentationException */ {
    return max_idGroup.toString() + TOKEN_SEPARATOR + groupNumber.toString();
  }


  @Override
  public void setValueFromPersistentRepresentation(String a_representation) throws UnsupportedRepresentationException,
                                                                                   UnsupportedOperationException {
    StringTokenizer tokenizer =
      new StringTokenizer(a_representation, TOKEN_SEPARATOR);

    if (tokenizer.countTokens() != 2)
      throw new UnsupportedRepresentationException("Unknown representation format: Two tokens expected!");
    try {
      max_idGroup = Integer.parseInt(tokenizer.nextToken());
      groupNumber = new Integer(tokenizer.nextToken());
    } catch (NumberFormatException ex) {
      throw new UnsupportedRepresentationException("Unknown representation format: Expecting integer values!");
    }
  }


  @Override
  public int compareTo(Object a_otherGroupGene) {
    if (a_otherGroupGene == null)
      return 1;

    if (groupNumber == null) {

      if (((GroupGene)a_otherGroupGene).groupNumber == null)
        return 0;
      else
        return -1;
    }

    return groupNumber.compareTo(((GroupGene)a_otherGroupGene).groupNumber);
  }

  @Override
  public boolean equals(Object a_otherGroupGene) {
    return a_otherGroupGene instanceof GroupGene &&
      compareTo(a_otherGroupGene) == 0;
  }

  @Override
  public int hashCode() {
    return groupNumber;
  }


  @Override
  public Object getInternalValue() {
    return groupNumber;
  }

  @Override
  public void applyMutation(int a_index, double a_percentage) {
    //        System.out.println("GroupGene applyMutation invoked");
    setAllele(getConfiguration().getRandomGenerator().nextInt(max_idGroup));
  }

  public Integer getGroupSize() {
    return groupSize;
  }

  public Integer getMax_idGroup() {
    return max_idGroup;
  }

  public static void setAllGroupSize(Integer a_inputGroupSize, int a_index) {
    GroupGene.allGroupSize[a_index] = a_inputGroupSize;
  }

    public static void setStudy_plan(Integer[] a_lessons, Integer[] a_times, int a_index) {
        int lesson_index = 0;
        int times_index = 0;
        
        for (int i = 0; i < a_lessons.length; i++) {
            if( a_lessons[i] != null ){
                lesson_index++;
                for (int j = 0; j < a_times.length; j++) {
                    if( a_lessons[i] != null) i++;               
                }                
            }
        }
    }

    public static void setAllIdGroup(Integer a_allIdGroup, int a_index) {
        allIdGroup[a_index] = a_allIdGroup;
    }

    public static void setAllNames(String a_allNames, int a_index) {
        allNames[a_index] = a_allNames;
    }

    public Integer getIdGroup() {
        return idGroup;
    }

    public String getName() {
        return name;
    }

    public static String[] getAllNames() {
        return allNames;
    }
}
