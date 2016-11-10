package com.redhat.bpms.examples.mortgage;

/**
 * This class was automatically generated by the data modeler tool.
 */

@javax.persistence.Entity
public class Applicant implements java.io.Serializable
{

   static final long serialVersionUID = 1L;

   @javax.persistence.GeneratedValue(strategy = javax.persistence.GenerationType.AUTO, generator = "APPLICANT_ID_GENERATOR")
   @javax.persistence.Id
   @javax.persistence.SequenceGenerator(sequenceName = "APPLICANT_ID_SEQ", name = "APPLICANT_ID_GENERATOR")
   private Long id;

   @org.kie.api.definition.type.Label(value = "Applicant Name")
   private String name;

   @org.kie.api.definition.type.Label(value = "Social Security Number")
   private Integer ssn;

   @org.kie.api.definition.type.Label(value = "Annual Income")
   private Integer income;

   @org.kie.api.definition.type.Label(value = "Credit Score")
   private Integer creditScore;

   public Applicant()
   {
   }

   public Long getId()
   {
      return this.id;
   }

   public void setId(Long id)
   {
      this.id = id;
   }

   public String getName()
   {
      return this.name;
   }

   public void setName(String name)
   {
      this.name = name;
   }

   public Integer getSsn()
   {
      return this.ssn;
   }

   public void setSsn(Integer ssn)
   {
      this.ssn = ssn;
   }

   public Integer getIncome()
   {
      return this.income;
   }

   public void setIncome(Integer income)
   {
      this.income = income;
   }

   public Integer getCreditScore()
   {
      return this.creditScore;
   }

   public void setCreditScore(Integer creditScore)
   {
      this.creditScore = creditScore;
   }

   public Applicant(Long id, String name,
         Integer ssn, Integer income,
         Integer creditScore)
   {
      this.id = id;
      this.name = name;
      this.ssn = ssn;
      this.income = income;
      this.creditScore = creditScore;
   }

}