# 引言
NBA数据分析系统API接口文档是NBA数据分析系统对外提供API接口服务的规范化文档，旨在指导API的使用者正确使用API接口服务。
NBA数据分析系统对外提供的API包括球队、球员和比赛的信息和数据查询。客户端可以通过HTTP的Get方法向服务器发送请求，服务器返回的是基于JSON格式的数据（分为JSONObject和JSONArray两种），使用者可自行解析获取到的JSON格式数据。

# API
## Team
### 1.查询所有球队信息

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

### 2.查询某球队的信息和数据

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

### 1.查询所有球员

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

### 2.根据名字首字母查询球员

#### 功能
根据名字首字母来查询数据库中包含的球员的名字。

#### API参数解释
|参数|解释|
|:----:|----|
|inital|名字首字母，必选参数，参数值必须为26个字母之一|

#### 返回值格式
返回值是一个JSONArray，JSONArray的每一个元素是一个球员的名字。

#### 返回值示例
```java
"Xavier Henry",
"Xavier Silas"
```

### 3.根据所在球队和赛季查询球员

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

### 4.查询某球员的信息和数据

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
|exp|球龄，-1表示已退役|String|1|
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
