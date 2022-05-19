# 引言
NBA数据分析系统API接口文档是NBA数据分析系统对外提供API接口服务的规范化文档，旨在指导API的使用者正确使用API接口服务。
NBA数据分析系统对外提供的API包括球队、球员和比赛的信息和数据查询。客户端可以通过HTTP的Get方法向服务器发送请求，服务器返回的是基于JSON格式的数据（分为JSONObject和JSONArray两种），使用者可自行解析获取到的JSON格式数据。

# API
## Team
### 1. 查询所有球队信息

#### 功能
查询三十支球队的基本信息，包括球队缩写、名称、所在城市、所在联盟、所在分区和建立年份。

#### 返回值格式
返回值是一个JSONArray，代表三十支球队信息的列表；JSONArray里面的每一个元素是一个JSONObject，代表一支球队的基本信息。

#### 返回值示例
``` java
{
	"buildup_time":"1950",
	"division":"Southeast",
	"league":"E",
	"name":"Atlanta Hawks",
	"location":"Atlanta,Georgia",
	"abbr":"ATL"
	},
	...
	{
	"buildup_time":"1947",
	"division":"Atlantic",
	"league":"E",
	"name":"Boston Celtics",
	"location":"Boston, Massachusetts",
	"abbr":"BOS"
	}
```
#### 返回值解释
| 键 | 含义 | 数据类型 | 示例 |
|:----:|:----:|:----:|:----:|
|abbr|球队缩写|String|ATL|
|name|球队名称|String|Atlanta Hawks|
|location|球队所在城市|String|Atlanta, Georgia|
|league|球队所在联盟（东部或西部）|String|E/W|
|division|球队所在分区（六个分区）|String|Southeast|
|buildup_time|球队建立年份|String|1947|

### 2. 查询某球队的信息和数据

#### 功能
查询某球队的信息和数据，可根据球队缩写、赛季来查询球队信息、总数据、场均数据、高阶数据、对手总数据、对手场均数据。

#### API参数解释
| 参数 | 解释 |
|:----:|----|
|abbr|球队缩写，必选参数，abbr可从上一条API获取，用于指定查询的球队|
|season|赛季，可选参数，参数形式如21-22，用于指定查询数据的赛季；如无此参数，则返回该球队所有赛季的数据|
|total|球队总数据，可选参数，参数值为0或1，无此参数时默认值为0；参数值为0时，表示不查询总数据，为1则查询|
|pergame|球队场均数据，可选参数，参数值为0或1，无此参数时默认值为0；参数值为0时，表示不查询场均数据，为1则查询|
|advanced|球队高阶数据数据，可选参数，参数值为0或1，无此参数时默认值为0；参数值为0时，表示不查询高阶数据，为1则查询|
|opptotal|对手总数据，可选参数，参数值为0或1，无此参数时默认值为0；参数值为0时，表示不查询对手总数据，为1则查询|
|opppergame|对手场均数据，可选参数，参数值为0或1，无此参数时默认值为0；参数值为0时，表示不查询对手场均数据，为1则查询|
> 注：若只有abbr参数或只有abbr和season两个参数，则只查询该球队信息。

#### 返回值格式
返回值是一个JSONObject，内含abbr、info、total、pergame、advanced、opptotal、opppergame键，它们的值各自是一个JSONObject。其中total、pergame、advanced、opptotal、opppergame的JSONObject含有一个或多个名为相应赛季的键，它们的值也各自是一个JSONObject。

#### 返回值示例
abbr = HOU & season = 21-22 & total = 1 & pergame = 0 & advanced = 0 & opptotal = 0 & opppergame = 0

``` java
{
	"total":
	{
		"21-22":{
			"fg":3032,
			"fga":6832,
			"ast":1820,
			"losses":26,
			"fg3_pct":0.348,
			"ft":1525,
			"fg2":2099,
			"fta":2133,
			"fg3":933,
			"drb":2624,
			"finish":1,
			"tov":1366,
			"orb":958,
			"height":"6-6",
			"fg2a":4152,
			"fg2_pct":0.506,
			"wins":56,
			"fga_pct":0.444,
			"fg3a":2680,
			"blk":407,"
			trb":3582,
			"weight":211,
			"stl":777,
			"ft_pct":0.715,
			"pts":8522,
			"minute":19805,
			"num_of_game":82,
			"pf":1803,
			"age":27.6
		}
    }
	"abbr":"HOU",
	"info":{
		"buildup_time":"1968",
		"division":"Southwest",
		"championships":2,
		"league":"W",
		"record":"2011-1877",
		"name":"Houston Rockets",
		"location":"Houston, Texas",
		"playeroff_appearance":29
	}
}
```
#### 返回值解释
* info:

| 键 | 含义 | 数据类型 | 示例 |
|:----:|:----:|:----:|:----:|
|abbr|球队缩写|String|ATL|
|name|球队名称|String|Atlanta Hawks|
|location|球队所在城市|String|Atlanta, Georgia|
|league|球队所在联盟（东部或西部）|String|E/W|
|division|球队所在分区（六个分区）|String|Southeast|
|buildup_time|球队建立年份|String|1947|
playeroff_appearance|球队参加季后赛的次数|Integer|29|
championships|球队获得季后赛总冠军的次数|Integer|2|

* total / pergame:

| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|wins|胜场（仅total）|Integer|
|losses|输场（仅total）|Integer|
|finish|常规赛积分榜名次（仅total）|Integer|
|age|球员平均年龄（仅total）|Double|
|height|球员平均身高（仅total）|String|
|weight|球员平均体重（仅total）|Double|
|num_of_game|赛季比赛场数（仅total）|Integer|
|minute|比赛时间|Integer/Double|
|fg|投篮命中数|Integer/Double|
|fga|投篮出手数|Integer/Double|
|fga_pct|投篮命中率|Integer/Double|
|fg3|三分命中数|Integer/Double|
|fg3a|三分出手数|Integer/Double|
|fg3_pct|三分命中率|Double|
|fg2|两分命中数|Integer/Double|
|fg2a|两分出手数|Integer/Double|
|fg2_pct|两分命中率|Double|
|ft|罚球命中数|Integer/Double|
|fta|罚球出手数|Integer/Double|
|ft_pct|罚球命中率|Double|
|orb|进攻篮板数|Integer/Double|
|drb|防守篮板数|Integer/Double|
|trb|总篮板数|Integer/Double|
|ast|助攻数|Integer/Double|
|stl|抢断数|Integer/Double|
|blk|盖帽数|Integer/Double|
|tov|失误数|Integer/Double|
|pf|犯规数|Integer/Double|
|pts|得分|Integer/Double|

* advanced:

| 键 | 含义 | 数据类型 |
|:----:|----|:----:|
|pw|Pythagorean wins, i.e., expected wins based on points scored and allowed.|Double|
|pl|Pythagorean losses, i.e., expected losses based on points scored and allowed.|Double|
|mov|Margin of Victory.|Double|
|sos|Simple Rating System. A team rating that takes into account average point differential and strength of schedule.|Double|
|srs|Simple Rating System. A team rating that takes into account average point differential and strength of schedule.|Double|
|off_rtg|Offensive Rating. An estimate of points produced (players) or scored (teams) per 100 possessions.|Double|
|def_rtg|Defensive Rating. An estimate of points allowed per 100 possessions.|Double|
|pace|Pace Factor. An estimate of possessions per 48 minutes.|Double|
|fta_per_fga_pct|Free Throw Attempt Rate.|Double|
|fg3a_per_fga_pct|3-Point Attempt Rate.|Double|
|off_efg_pct|Offense Effective Field Goal Percentage.|Double|
|off_tov_pct|Offense Turnover Percentage.|Double|
|orb_pct|进攻篮板率|Double|
|off_ft_rate|Offense Free Throws Per Field Goal Attempt|Double|
|opp_efg_pct|Defense Effective Field Goal Percentage.|Double|
|opp_tov_pct|Defense Turnover Perce.|Double|
|drb_pct|防守篮板率|Double|
|opp_ft_rate|Defense Free Throw per Field Goal Attempt|Double|
|arena|比赛主场|String|
|attendance|观众总人数|Integer|

* opptotal/opppergame:
  
| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|num_of_game|赛季比赛场数（仅total）|Integer|
|minute|比赛时间|Integer/Double|
|fg|对手投篮命中数|Integer/Double|
|fga|对手投篮出手数|Integer/Double|
|fga_pct|对手投篮命中率|Double|
|fg3|对手三分命中数|Integer/Double|
|fg3a|对手三分出手数|Integer/Double|
|fg3_pct|对手三分命中率|Double|
|fg2|对手两分命中数|Integer/Double|
|fg2a|对手两分出手数|Integer/Double|
|fg2_pct|对手两分命中率|Double|
|ft|对手罚球命中数|Integer/Double|
|fta|对手罚球出手数|Integer/Double|
|ft_pct|对手罚球命中率|Double|
|orb|对手进攻篮板数|Integer/Double|
|drb|对手防守篮板数|Integer/Double|
|trb|对手总篮板数|Integer/Double|
|ast|对手助攻数|Integer/Double|
|stl|对手抢断数|Integer/Double|
|blk|对手盖帽数|Integer/Double|
|tov|对手失误数|Integer/Double|
|pf|对手犯规数|Integer/Double|
|pts|对手得分|Integer/Double|

## Player

### 1. 查询所有球员

#### 功能
查询数据库中包含的所有球员的名字。

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一个球员的名字。

#### 返回值示例
``` java
"A.C. Green",
"A.J. Guyton",
"A.J. Price",
"Aaron Brooks",
...
"Zach Randolph",
"Zan Tabak",
"Zarko Cabarkapa",
"Zaza Pachulia",
"Zeljko Rebraca",
"Zendon Hamilton",
"Zoran Dragic",
"Zoran Planinic",
"Zydrunas Ilgauskas"
```

### 2. 根据名字首字母查询球员

#### 功能
根据名字首字母来查询数据库中包含的球员的名字。

#### API参数解释
|参数|解释|
|:----:|----|
|inital|名字首字母，必选参数，参数值必须为26个字母之一|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一个球员的名字。

#### 返回值示例
initial=X
```java
"Xavier Henry",
"Xavier Silas"
```

### 3. 根据所在球队和赛季查询球员

#### 功能
根据所在球队和赛季查询数据库中包含的球员的名字，其中的球队为球员曾经或当今所在的球队。

#### API参数解释
|参数|解释|
|:----:|----|
|team|所在球队缩写，必选参数，可从TeamAPI中获取|
|season|赛季，可选参数，如21-22|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一个球员的名字。

#### 返回值示例
team = HOU
```java
"Aaron Brooks",
"Adrian Griffin",
"Alexey Shved",
"Alton Ford",
"Andre Barrett",
"Anthony Miller",
...
"Yao Ming"
```

team = HOU & season = 21-22
```java
"Alexey Shved",
"Clint Capela",
"Corey Brewer",
"Donatas Motiejunas",
"Dwight Howard",
"Francisco Garcia",
"Isaiah Canaan",
"James Harden",
"Jason Terry",
"Joey Dorsey",
"Josh Smith",
"K.J. McDaniels",
"Kostas Papanikolaou",
"Nick Johnson",
"Pablo Prigioni",
"Patrick Beverley",
"Tarik Black",
"Terrence Jones",
"Trevor Ariza",
"Troy Daniels"
```

### 4. 查询某球员的信息和数据

#### 功能
查询某球员的信息和数据，可根据球员名字、赛季来查询球员信息、球员薪水、常规赛/季后赛的球员总数据、场均数据和高阶数据。

#### API参数解释
|参数|解释|
|:----:|----|
|name|球员名字，必选参数，name可从上面的API获取，用于指定查询的球员|
|season|赛季，可选参数，参数形式如21-22，用于指定查询数据的赛季。如无此参数，则返回该球员所有赛季的数据和薪水|
|regular|常规赛/季后赛，可选参数，参数值为0或1无此参数时表示常规赛和季后赛的数据都查询，0表示只查询季后赛数据，1表示只查询常规赛数据|
|total|球员总数据，可选参数，参数值为0或1，无此参数时默认值为0。参数值为0时，表示不查询总数据，为1则查询|
|pergame|球员场均数据，可选参数，参数值为0或1，无此参数时默认值为0。参数值为0时，表示不查询场均数据，为1则查询|
|advanced|球员高阶数据数据，可选参数，参数值为0或1，无此参数时默认值为0。参数值为0时，表示不查询高阶数据，为1则查询|
|salary|薪水，可选参数，参数值为0或1，无此参数时默认值为0。参数值为0时，表示不查询薪水，为1则查询|
>注：若只有name参数或只有name和season、regular任一/两个可选参数，则只查询该球员信息。

#### 返回值格式
返回值是一个JSONObject，内含name、info、total、pergame、advanced、salary键，它们的值各自是一个JSONObject。其中total、pergame、advanced、salary的JSONObject含有一个或多个名为相应赛季的键，它们的值也各自是一个JSONObject。

#### 返回值示例
name = Yao Ming & season = 08-09 & regular = 0 & total = 1 & pergame = 0 & advanced = 0 & salary = 1

```java
"name":"Yao Ming",
	"total_regular":{
		"08-09":{
			"fg":566,"
			fga":1032,
			"game_start":77,
			"ast":137,
			"fg3_pct":1,
			"ft":381,
			"fg2":565,
			"fta":440,
			"fg3":1,
			"drb":557,
			"tov":234,
			"orb":204,
			"fg2a":1031,
			"fg2_pct":0.548,
			"fga_pct":0.548,
			"fg3a":1,
			"blk":150,
			"trb":761,
			"stl":30,
			"ft_pct":0.866,
			"team_abbr":"HOU",
			"pts":1514,
			"minute":2589,
			"num_of_game":77,
			"efg_pct":0.549,
			"pf":257,
			"position":"C"
		}
	},
	"salary":{
		"08-09":{
			"team":"HOU",
			"salary":15070550
		}
	},
	"info":{
		"college":"",
		"debut":"October 30, 2002",
		"number":11,
		"hometown":"Shanghai, China",
		"born":"1980-09-12",
		"draft":"Houston Rockets, 1st round (1st pick, 1st overall), 2002 NBA Draft",
		"weight":310,
		"position":"C",
		"exp":-1,
		"high_school":"",
		"height":"7-6",
		"shoots":"Right"
	}
}
```

#### 返回值解释

* info

| 键 | 含义 | 数据类型 | 示例 |
|:----:|:----:|:----:|:----:|
|name|球员名字|String|Yao Ming|
|born|出生日期|String|1980-09-12|
|hometown|出生地（城市,国家）|String|Shanghai, China|
|position|位置|String|C,PF,SF,PG,SG|
|height|身高|String|7-6|
|weight|体重|String|310|
|shoots|shoots|String|Left,Right|
|high_school|高中|String|\|
|college|大学|String|\|
|draft|选秀|String|\|
|debut|初次登场|String|\|
|exp|球龄，-1表示已退役|String|-1|
|number|球衣号|String|11|

* total/pergame

| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|team_abbr|所在球队缩写|String|
|position|位置|String|
|num_of_game|比赛场数（仅total）|Integer|
|game_started|首发场数（仅total）|Integer|
|minute|在场时间|Integer/Double|
|fg|投篮命中数|Integer/Double|
|fga|投篮出手数|Integer/Double|
|fga_pct|投篮命中率|Double|
|fg3|三分命中数|Integer/Double|
|fg3a|三分出手数|Integer/Double|
|fg3_pct|三分命中率|Double|
|fg2|两分命中数|Integer/Double|
|fg2a|两分出手数|Integer/Double|
|fg2_pct|两分命中率|Double|
|ft|罚球命中数|Integer/Double|
|fta|罚球出手数|Integer/Double|
|ft_pct|罚球命中率|Double|
|orb|进攻篮板数|Integer/Double|
|drb|防守篮板数|Integer/Double|
|trb|总篮板数|Integer/Double|
|ast|助攻数|Integer/Double|
|stl|抢断数|Integer/Double|
|blk|盖帽数|Integer/Double|
|tov|失误数|Integer/Double|
|pf|犯规数|Integer/Double|
|pts|个人得分|Integer/Double|

* advance

| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|team_abbr|所在球队缩写|String|
|position|位置|String|
|num_of_game|比赛场数（仅total）|Integer|
|game_started|首发场数（仅total）|Integer|
|minute|在场时间|Integer/Double|
|per|player efficiency rating|Double|
|ts_pct|true shooting percentage|Double|
|fa3a_per_fga_pct|3-point attempt rate|Double|
|fta_per_fga_pct|free throw attempt rate|Double|
|orb_pct|进攻篮板率|Double|
|drb_pct|防守篮板率|Double|
|trb_pct|总篮板率|Double|
|ast_pct|助攻率|Double|
|stl_pct|抢断率|Double|
|blk_pct|盖帽率|Double|
|tov_pct|失误率|Double|
|usg_pct|使用率|Double|
|ows|offensive win shares|Double|
|dws|defensive win shares|Double|
|ws|win shares|Double|
|ws_48|win shares per 48 mins|Double|
|obpm|offensive box plus/minus|Double|
|dbpm|defensive box plus/minus|Double|
|bpm|box plus/minus|Double|
|vorp|value over replacement player|Double|

## Match

### 1. 根据起始日期和截止日期查询比赛

#### 功能
根据指定的起始日期和截止日期查询比赛日期在选定区间内的比赛编号ID。

#### API参数解释
|参数|解释|
|:---:|---|
|begin|起始日期，必选参数，日期格式必须如同2021-12-01|
|end|截止日期，必选参数，日期格式必须如同2021-12-05|
>注：如果截止日期小于起始日期，则返回为空的JSONArray。

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一场比赛的编号ID。

#### 返回值示例
begin=2022-01-01&end=2022-01-02
```java
[
	"202201010CHI-DEN",
	"202201010MIN-SAC",
	"202201020PHO-PHI",
	"202201020ORL-BRK",
	"202201020OKC-WAS",
	"202201020NYK-DET",
	"202201020NOP-HOU",
	"202201020MIL-IND",
	"202201020LAL-MEM",
	"202201020GSW-TOR",
	"202201020CHO-CLE",
	"202201020BOS-DAL",
	"202201020UTA-ATL"
]
```

### 2. 根据赛季查询本赛季的比赛

#### 功能
根据赛季查询本赛季的比赛/常规赛/季后赛，并且可以规定数量，根据日期排序。

#### API参数解释
|参数|解释|
|:---:|---|
|season|赛季，必选参数，指定查询比赛的赛季，赛季格式必须如同21-22|
|regular|常规赛/季后赛，可选参数，参数值为0或1，如无次参数则表示查询所有比赛，0则查询季后赛，1则查询常规赛|
|limit|数量限制，可选参数，数据类型是Integer|
|regular|按照日期正序/逆序，可选参数，参数值为0或1，无此参数时默认为0。值为0时，按日期正序排列，为1时按日期逆序排列|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一场比赛的编号ID。

#### 返回值示例
season=21-22&regular=0&limit=5&desc=0
```java
[
	"202204190LAC-GSW",
	"202204190OKC-MEM",
	"202204190IND-ATL",
	"202204190TOR-BRK",
	"202204200MIA-CHA"
]
```

### 3. 根据赛季和球队查询相关比赛

#### 功能
根据赛季和球队查询本赛季该球队的比赛/常规赛/季后赛，并且可以规定数量，根据日期排序。

#### API参数解释
|参数|解释|
|:---:|---|
|season|赛季，必选参数，指定查询比赛的赛季，赛季格式必须如同21-22|
|team|球队缩写，必选参数，目的在于获取该球队参与的比赛|
|regular|常规赛/季后赛，可选参数，参数值为0或1，如无次参数则表示查询所有比赛，0则查询季后赛，1则查询常规赛|
|home|主场/客场，可选参数，参数值为0或1，如无次参数则表示查询所有比赛，0则查询客场比赛，1则查询主场比赛|
|limit|数量限制，可选参数，数据类型是Integer|
|desc|按照日期正序/逆序，可选参数，参数值为0或1，无此参数时默认为0。值为0时，按日期正序排列，为1时按日期逆序排列|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一场比赛的编号ID。

#### 返回值示例
season=21-22&team=HOU&regular=1&limit=5&desc=1
```java
[
	"202204150HOU-UTA",
	"202204130CHO-HOU",
	"202204120HOU-NOP",
	"202204100HOU-SAS",
	"202204080SAS-HOU"
]
```

### 4. 根据赛季和球员查询相关比赛

#### 功能
根据赛季和球员查询本赛季该球员的比赛/常规赛/季后赛，并且可以规定数量，根据日期排序。

#### API参数解释
|参数|解释|
|:---:|---|
|season|赛季，必选参数，指定查询比赛的赛季，赛季格式必须如同21-22|
|player|球员名字，必选参数，目的在于获取该球员参与的比赛|
|regular|常规赛/季后赛，可选参数，参数值为0或1，如无次参数则表示查询所有比赛，0则查询季后赛，1则查询常规赛|
|limit|数量限制，可选参数，数据类型是Integer|
|desc|按照日期正序/逆序，可选参数，参数值为0或1，无此参数时默认为0。值为0时，按日期正序排列，为1时按日期逆序排列|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一场比赛的编号ID。

#### 返回值示例
season=08-09&player=Yao%20Ming&regular=1&limit=5&desc=1
```java
[
	"200904150DAL-HOU",
	"200904130HOU-NOH",
	"200904090SAC-HOU",
	"200904070HOU-ORL",
	"200904050HOU-POR"
]
```

### 5. 查询某场比赛的信息和数据

#### 功能
查询某场比赛的信息和数据，可根据比赛编号ID来查询比赛信息、比赛得分、球员基础数据和球员高阶数据。

#### API参数解释
|参数|解释|
|:---:|---|
|id|比赛编号ID，必选参数，指定要查询的比赛，ID可从上面的API中获取|
|basic|球员基础数据，可选参数，参数值为0或1，无此参数时默认为0，0表示不查询球员基础数据，1表示查询|
|advanced|球员高阶数据，可选参数，参数值为0或1，无此参数时默认为0，0表示不查询球员高阶数据，1表示查询|

#### 返回值格式
返回值是一个JSONObject，内含game_id、info、scores、basic、advanced键，它们的值各自是一个JSONObject。其中scores含有两个名各自为主队和客队缩写的键，它们的值分别为主队和客队得分的JSONArray。basic和advanced的JSONObject含有两个名各自为主队和客队缩写的键，它们的值也是一个JSONArray，JSONArray含多个JSONObject。

#### 返回值示例
id=202112280LAL-HOU&basic=1&advanced=0
```java
{
	"scores":{
		"LAL":[19,26,24,21,90],
		"HOU":[31,31,23,23,108]
	},
	"basic":{
		"LAL":[
			{
				"fga_pct":0.2,
				"fg3a":1,
				"blk":0,
				"fg":1,
				"fga":5,
				"ast":6,
				"starter":"Starter",
				"trb":2,
				"ft_pct":0.833,
				"fg3_pct":0,
				"ft":5,
				"pts":7,
				"minute":29.133333333333333,
				"plus_minus":-16,
				"fta":6,
				"fg3":0,
				"drb":1,
				"pf":3,
				"tov":4,
				"player_name":"Jeremy Lin",
				"orb":1
			}
			...
		],"HOU":[
			...
		]
	},
	"info":{
		"date":"2021-12-28",
		"guest_point":108,
		"home_point":90,
		"is_normal":true,
		"season":"21-22",
		"location":"STAPLES Center, Los Angeles, California",
		"time":"2:46",
		"home_team":"LAL",
		"guest_team":"HOU"
	},
	"game_id":"202112280LAL-HOU"
}
```

#### 返回值解释

* info

| 键 | 含义 | 数据类型 | 示例 |
|:----:|:----:|:----:|:----:|
|gameid|比赛编号|String|123|
|season|赛季|String|21-22|
|date|比赛日期|String|2021-11-28|
|is_normal|常规赛还是季后赛|Boolean|true/false|
|location|比赛地点（场馆,城市,地区）|String|LA|
|home_team|主队缩写|String|HOU|
|guest_team|客队缩写|String|LAL|
|home_point|主队总得分|Integer|110|
|guest_point|客队总得分|Integer|121|
|time|比赛时长(时:分)|String|2:46|

* basis

| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|player_name|球员名字|String|
|starter|首发，TeamTotal球队总数据，Starter首发球员，Reserve是非首发上场，DidNotPlayer冷板凳|String|
|minute|在场时间|Double|
|fg|投篮命中数|Integer/Double|
|fga|投篮出手数|Integer/Double|
|fga_pct|投篮命中率|Double|
|fg3|三分命中数|Integer/Double|
|fg3a|三分出手数|Integer/Double|
|fg3_pct|三分命中率|Double|
|ft|罚球命中数|Integer/Double|
|fta|罚球出手数|Integer/Double|
|ft_pct|罚球命中率|Double|
|orb|进攻篮板数|Integer/Double|
|drb|防守篮板数|Integer/Double|
|trb|总篮板数|Integer/Double|
|ast|助攻数|Integer/Double|
|stl|抢断数|Integer/Double|
|blk|盖帽数|Integer/Double|
|tov|失误数|Integer/Double|
|pf|犯规数|Integer/Double|
|pts|个人得分|Integer/Double|
|plus_minus|+/-|Double|

* advance

| 键 | 含义 | 数据类型 |
|:----:|:----:|:----:|
|player_name|球员名字|String|
|starter|首发，TeamTotal球队总数据，Starter首发球员，Reserve是非首发上场，DidNotPlayer冷板凳|String|
|minute|在场时间|Double|
|efg_pct|effective field goal percentage|Double|
|ts_pct|true shooting percentage|Double|
|fa3a_per_fga_pct|3-point attempt rate|Double|
|fta_per_fga_pct|free throw attempt rate|Double|
|orb_pct|进攻篮板率|Double|
|drb_pct|防守篮板率|Double|
|trb_pct|总篮板率|Double|
|ast_pct|助攻率|Double|
|stl_pct|抢断率|Double|
|blk_pct|盖帽率|Double|
|tov_pct|失误率|Double|
|usg_pct|使用率|Double|
|off_rtg|进攻效率|Double|
|def_rtg|防守效率|Double|

## 通用返回值
|返回字符串|字符串解释|
|:---:|---|
|Not support|不支持当前的Get请求，请确认是否使用上述正确的API请求|
|Not found|数据库中检索不到当前所查询的数据，请确认API请求|
|Error|服务器发生错误，请联系|
