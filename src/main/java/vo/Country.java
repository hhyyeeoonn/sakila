package vo;

public class Country { //모든 클래스는 extends Object(Object는 상속받은 것 Abc를 상속받았으면 Object 대신 Abc라고 써도 됨 Object는 최상위타입이라서 모든 클래스는 오브젝트를 상속받는 것이라고 봐야함) 생략되어있다
	// private 정보은닉
	private int countryId;
	private String country;
	private String lastUpdate;
	public Country() { // source탭으로 자동생성가능
		super();
		this.countryId = 0;
		this.country = null;
		this.lastUpdate = null;
	}
	public int getCountryId() { // (Country this) 캡슐화 클래스를 만드는 자체는 추상화
		return this.countryId; // 외부에서 호출하는 객체가 this에 들어감
	}
	public void setCountryId(int countryId) {
		if(countryId < 0) {
			return; 
		}
		this.countryId=countryId;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(String lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
	
	
	
	/*
	public Country() { // 생성자메서드 클래스 이름과 동일하게 만들기 따로 쓰지 않아도 자동으로 만들어짐 보통 생략되어있음 static메서드에 가깝고 반환값은 void라고 봐야함
		super(); // 부모생성자를 호출함 extends 뒤에 있는 것을 호출함 따로 쓰지 않아도 자동으로 생성됨 
		this.countryId=0; // 기본타입은 0으로 초기화 boolean 타입은 false로 
		this.country=null; //참조타입은 null로 초기화
		this.lastUpdate=null;
	}
	*/
	

	
}
