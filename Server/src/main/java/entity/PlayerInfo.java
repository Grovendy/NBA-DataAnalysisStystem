package entity;

/**
 * 球员信息类
 *
 */
public class PlayerInfo {

	/**
	 * 姓名
	 */
	private String name;
	
	/**
	 * 出生年月
	 */
	private String born;
	
	/**
	 * 出生地
	 */
	private String hometown;
	
	/**
	 * 位置
	 */
	private String position;
	
	/**
	 * 身高
	 */
	private String height;
	
	/**
	 * 体重
	 */
	private Integer weight;
	
	/**
	 * shoots
	 */
	private String shoots;
	
	/**
	 * 高中
	 */
	private String high_school;
	
	/**
	 * 大学
	 */
	private String college;
	
	/**
	 * NBA Draft
	 */
	private String draft;
	
	/**
	 * NBA Debut 初次登场
	 */
	private String debut;
	
	/**
	 * NBA球龄（经验）
	 */
	private Integer exp;
	
	/**
	 * 球衣号
	 */
	private Integer number;
	
	/**
	 * 无参构造函数
	 */
	public PlayerInfo(){}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getBorn() {
		return born;
	}

	public void setBorn(String born) {
		this.born = born;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public String getShoots() {
		return shoots;
	}

	public void setShoots(String shoots) {
		this.shoots = shoots;
	}

	public String getHigh_school() {
		return high_school;
	}

	public void setHigh_school(String high_school) {
		this.high_school = high_school;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public String getDraft() {
		return draft;
	}

	public void setDraft(String draft) {
		this.draft = draft;
	}

	public String getDebut() {
		return debut;
	}

	public void setDebut(String debut) {
		this.debut = debut;
	}

	public Integer getExperience() {
		return exp;
	}

	public void setExperience(Integer experience) {
		this.exp = experience;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}
	
}
