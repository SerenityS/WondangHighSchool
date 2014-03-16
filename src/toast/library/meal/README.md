    - Android Meal Library with Mir(whdghks913) -

    - 한국어 버전 -
-원본 소스 : http://blog.naver.com/rimal
-ITcraft's Github Project의 오픈소스 : https://github.com/mhkim4886/OkdongMidSch/blob/master/src/toast/library/meal/MealLibrary.java
-원본 라이센스 : Public Open Library

-수정 : ~ 2014-03-16
-업로드 : https://bitbucket.org/whdghks913/wondanghighschool (src/toast/library/meal/MealLibrary.java)

-API 사용법
(1) getDate() : 일주일치 날짜를 반환합니다
-반환 타입 : String[]
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

일주일치 날짜를 반환합니다
schYmd 또는 year(month, day)를 입력하지 않을경우, 현재 서버 날짜를 기준으로 급식을 가져옵니다
schYmd 또는 year(month, day)를 입력해서 원하는 날짜의 한주 급식을 가져올수도 있습니다
String[]에서 [0]에는 일요일의 정보가, [6]에는 토요일의 정보가 담기며, 총 길이는 0~6 입니다


(2) getMeal() : 일주일치 급식을 반환합니다
-반환 타입 : String[]
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

일주일치 급식을 반환합니다
급식이 없을경우 " " 또는 "" 또는 null으로 반환하며, 자세한 설명은 getDate()와 같습니다


(3) getMonthMeal() : 한달 급식을 반환합니다
-반환 타입 : String[]
-getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYm)
-getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month)

한달 급식을 반환합니다
getMeal()에서 사용하는 schYm 파라메터와 getMonthMeal()에서 사용하는 schYm파라메터는 다른 형식을 사용해야 합니다 (아래 참조)
String[]의 길이는 한달 날짜 길이와 같으며, 2월은 윤년을 위해 길이가 29입니다


(4) getKcal() : 일주일치 급식의 칼로리를 가져옵니다
-반환 타입 : String[]
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

일주일치 급식의 칼로리를 가져옵니다
getDate()와 같습니다


(5) getPeople() : 일주일치 급식 인원을 반환합니다
-반환 타입 : String[]
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

일주일치 급식 인원을 반환합니다
getDate()와 같습니다


(6) 변수 설명
-CountryCode : 학교 교육청 코드, nice홈페이지 도메인과 같습니다
EX) 인천 : ice.go.kr

-schulCode : 학교 고유 나이스 코드
EX) 인천의 학교 코드 검색 : http://hes.ice.go.kr/sts_sci_si00_001.do (E10000xxxx)
참조 : 중학교 나이스 코드 검색 : http://me2.do/xAY6Zij1
고등학교 나이스 코드 검색 : http://me2.do/G22fDh8l

-schulCrseScCode, schulKndScCode : 학교 고유 분류 번호
: 어디서 구하는지를 잘 모르는 부분입니다만, Toast님께서 schulCrseScCode = "4", schulKndScCode = "04"으로 사용하셨습니다
나이스 홈페이지에는 schulCrseScCode는 학교종과정분코드, schulKndScCode는 학교종류구분코드 라고 주석처리 되어 있었습니다

-schMmealScCode : 급식 코드
조식 : "1"
중식 : "2"
석식 : "3"

-schYmd : (일주일치 정보를 얻어오는 메소드에서) 원하는 날짜의 급식 정보를 얻기 위해 필요
String 형식 : 년.월.일
EX) "2014.03.16"

-schYm : (한달치 정보를 얻어오는 메소드에서) 원하는 달의 급식 정보를 얻기 위해 필요
String 형식 : 년.월
EX) "2014.03"

-year, month, day : schYmd와 schYm의 정보를 세분화 해서 각각 파라메터 사용 가능
EX) year = "2014", month = "03", day = "16"





    - EN Version -
-Origin Source : http://blog.naver.com/rimal
-OpenSource by ITcraft's Github Project : https://github.com/mhkim4886/OkdongMidSch/blob/master/src/toast/library/meal/MealLibrary.java
-Origin License : Public Open Library

-Fixed : ~ 2014-03-16
-Upload : https://bitbucket.org/whdghks913/wondanghighschool (src/toast/library/meal/MealLibrary.java)

-How To API
(1) getDate() : You can get the days of week
-return : String[]
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getDate(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

return the date of a week


(2) getMeal() : You can get the days of week School Meal
-return : String[]
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

return the meal that day of week


(3) getMonthMeal() : You can get the meal of a month
-return : String[]
-getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYm)
-getMonthMeal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month)

return the month meal


(4) getKcal() : You can get the days of week School Kcal
-return : String[]
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getKcal(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

return the kcal that day of week


(5) getPeople() : You can get the days of week School People
-return : String[]
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode)
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String schYmd)
-getPeople(String CountryCode, String schulCode, String schulCrseScCode, String schulKndScCode, String schMmealScCode, String year, String month, String day)

return the people that day of week


(6) Description of the parameters
-CountryCode : Your School's City Website
EX) Incheon : ice.go.kr

-schulCode : School's Nice Code
EX) Incheon's School Search : http://hes.ice.go.kr/sts_sci_si00_001.do (E10000xxxx)

-schulCrseScCode, schulKndScCode : Classification number of a school
: I don't Understand here but usually schulCrseScCode = "4", schulKndScCode = "04"

-schMmealScCode : Meal Code
Breakfast : "1"
lunch : "2"
dinner : "3"

-schYmd : Get Custom Meal (Day of Week)
String Type : year.month.day
EX) "2014.03.16"

-schYm : Get Custom Meal (a Month)
String Type : year.month
EX) "2014.03"

-year, month, day : To subdivide the schYmd and schYm
EX) year = "2014", month = "03", day = "16"