# -*- coding: utf-8 -*-

import urllib2
import re
import time
import os
import random
import ssl

main_url = 'http://www.basketball-reference.com/'
match_url = main_url + 'boxscores/'

ssl_ctx = ssl.create_default_context()
ssl_ctx.check_hostname = False
ssl_ctx.verify_mode = ssl.CERT_NONE

replayer = []
nba_teams = []


def begin():
    print u'NBA数据采集程序 V1.0'
    print '===================='

    print u'请输入需要爬取的比赛日期：[20141001-20141231]'
    # 20220322
    get_date = '20141028-20141104'
    # scrape_match(get_date)
    # check_player()
    # print u'是否需要爬取球队基本数据？[Y/N]'
    # get_team = raw_input('> ')
    # print u'是否需要爬取球队具体数据？[Y/N]'
    # get_team_data = raw_input('> ')
    # print u'是否需要爬取球队Logo？[Y/N]'
    # get_tlogo = raw_input('> ')
    # print u'是否需要爬取球员数据？[Y/N]'
    # get_player = raw_input('> ')
    # print u'是否需要爬取在役球员头像？[Y/N]'
    # get_now_pic = raw_input('> ')
    # print u'是否需要爬取所有球员头像？[Y/N]'
    # get_pic = raw_input('> ')
    check_player()
    get_team_list()
    scrape_team_by_local()
    # get_team_list()
    # scrape_team_logo()
    # scrape_player()
    # scrape_player_now_pic()
    # scrape_player_pic()
    print u'数据爬虫已结束，自动退出程序...'


def scrape_match(date):
    start_year = int(date[0:4])
    start_month = int(date[4:6])
    start_day = int(date[6:8])
    end_year = int(date[9:13])
    end_month = int(date[13:15])
    end_day = int(date[15:17])

    if start_month < 9:
        season = (start_year - 1).__str__() + '-' + start_year.__str__()
    else:
        season = start_year.__str__() + '-' + (start_year + 1).__str__()

    path = 'data/matches/' + season
    if os.path.isdir(path):
        pass
    else:
        os.mkdir(path)

    f_matches = open(path + '.txt', 'a')

    for year in range(start_year, end_year + 1):
        for month in range(1, 13):
            if year == start_year and month < start_month:
                continue
            if year == end_year and month > end_month:
                continue
            for day in range(1, 32):
                if year == start_year and month == start_month and day < start_day:
                    continue
                if year == end_year and month == end_month and day > end_day:
                    continue
                print year, month, day
                url = match_url + 'index.cgi?month=' + month.__str__() + '&day=' + \
                      day.__str__() + '&year=' + year.__str__()
                response = urllib2.urlopen(url, context=ssl_ctx)
                items = re.findall(r'<a href="/boxscores/(.*?)\.html">F',
                                   response.read())
                if len(items) != 0:
                    print items
                for item in items:
                    f_matches.write(item + '\n')
                    scrape_each_match(item, path)
                    time.sleep(0.2)
    f_matches.close()
    pass


def scrape_each_match(item, path):
    url = match_url + item + '.html'
    response = urllib2.urlopen(url, context=ssl_ctx)
    html = response.read()
    # result = re.search('<td><a href="/boxscores/' + item + r'.html">(.*?)<br>(.*?)</a>', html)
    result = re.findall('html">(.*)</a></td>\s*<td class="right">(.*)</td>', html)
    guest_team, guest_point = result[0]
    home_team, home_point = result[1]

    guest_point = [guest_point]
    home_point = [home_point]

    game_id = item + '-' + guest_team
    result = re.search('Lost', html)
    if result is None:
        regular = False
    else:
        regular = True
    result = re.search('<div class="scorebox_meta">\s*<div>(.*)</div><div>(.*)</div>', html)
    if result is None:
        result = re.search(r'<td class="align_center padding_bottom small_text" colspan="2">(.*?)</td>', html)
        date = result.groups()[0]
        location = ''
    else:
        date = result.groups()[0]
        location = result.groups()[1]

    result = re.search('Time of Game:&nbsp;</strong>(.*)</div>', html)
    if result is None:
        time_of_game = ''
    else:
        time_of_game = result.groups()[0]
    f_match = open(path + '/' + game_id, 'w')
    f_player = open('data/players/players.txt', 'a')
    f_match.write(game_id + ';' + home_team + ';' + guest_team + ';' + str(regular) + ';' + date + ';' +
                  location + ';' + time_of_game + '\n')
    for item in guest_point:
        f_match.write(item + ';')
    f_match.write('\n')
    for item in home_point:
        f_match.write(item + ';')
    f_match.write('\n')

    tables = re.findall(r'<table .*?-game-.*?>([\s\S]*?)</table>', html)
    i = 0
    for table in tables:
        table = table.replace('\n', '')
        tbody = re.findall('<tbody>(.*?)</tbody>', table)[0]
        if i == 0:
            f_match.write('Guest Basic\n')
        elif i == 1:
            f_match.write('Guest Advanced\n')
        elif i == 2:
            f_match.write('Home Basic\n')
        else:
            f_match.write('Home Advanced\n')

        trs = re.findall(r'<tr >(.*?)</tr>', tbody)
        for tr in trs:
            href, name = re.findall(r'<th.*?><a href="(.*?)">(.*?)</a></th>', tr)[0]
            tds = re.findall(r'<td.*?\s>(.*?)</td>', tr)
            f_player.write(name + ';' + href + '\n')
            f_match.write(name + ';')
            for td in tds:
                f_match.write(td + ';')
            f_match.write('\n')
        i += 1

        tfoot = re.findall('<tfoot>(.*?)</tfoot>', table)
        tr = re.findall(r'<tr >(.*?)</tr>', tfoot[0])[0]
        tds = re.findall(r'<td.*?>(.*?)</td>', tr)
        f_match.write('Team Totals' + ';')
        for td in tds:
            f_match.write(td + ';')
        f_match.write('\n')

    f_player.close()
    f_match.close()


def check_player():
    f = open('data/players/players.txt', 'r')
    lines = f.readlines()
    lines = list(set(lines))
    lines.sort()
    f.close()
    f = open('data/players/players.txt', 'w')
    for line in lines:
        f.write(line)
    f.close()


def scrape_player():
    f = open('data/players/players.txt', 'r')
    lines = f.readlines()
    for line in lines:
        line = line.strip('\n')
        temp = line.split(';')
        scrape_each_player(temp[0], temp[1])
        time.sleep(0.2)

    f.close()


def scrape_each_player(name, url):
    url = main_url + url[1:]
    response = urllib2.urlopen(url, context=ssl_ctx)
    html = response.read()
    if os.path.isfile('data/players/text/' + name):
        name = name + '-' + random.randint(1, 9).__str__()
        replayer.append(name)
    f = open('data/players/text/' + name, 'w')
    print name
    position = re.findall(r'Position:\s*</strong>([\s\S]*?)&', html)[0].strip('\n ')
    shoots = re.findall(r'Shoots:\s*</strong>([\s\S]*?)<', html)[0].strip('\n ')
    born = re.search(r'data-birth="(.*?)"', html).groups()[0].strip('\n ')
    result = re.search(r'</span> in (.*?)<a .*?>(.*?)</a>', html)
    if result is not None:
        hometown = result.groups()[0].strip('\n ') + result.groups()[1].strip('\n ')
    else:
        hometown = ''
    height = re.search(r'itemprop="height">(.*?)</span>', html).groups()[0].strip('\n ')
    weight = re.search(r'itemprop="weight">(.*?)lb</span>', html).groups()[0].strip('\n ')
    result = re.search(r'High School:\s*</strong>\s*([\s\S]*?)\s*<a .*?>(.*?)</a>', html)
    if result is None:
        result = re.search(r'High School:</span> (.*?)\n', html)
        if result is None:
            high_school = ''
        else:
            high_school = result.groups()[0].strip('\n ')
    else:
        high_school = result.groups()[0] + result.groups()[1].strip('\n ')
    result = re.search(r'College:</span> <a .*?>(.*?)</a>', html)
    if result is None:
        college = ''
    else:
        college = result.groups()[0]
    result = re.search(r'Draft:</span> <a .*?>(.*?)</a>(.*?)<a .*?>(.*?)</a>', html)
    if result is None:
        draft = ''
    else:
        draft = result.groups()[0] + result.groups()[1] + result.groups()[2]
    result = re.search(r'NBA Debut:</span> <a .*?">(.*?)</a>', html)
    if result is None:
        debut = ''
    else:
        debut = result.groups()[0]
    result = re.search(r'Experience:</span> (.*?) years', html)
    if result is None:
        experience = '-1'
    else:
        experience = result.groups()[0]
    result = re.findall(r'<span style=\'font-size.*?>(.*?)</span>', html)
    if result is None or len(result) == 0:
        number = ''
    else:
        number = result[len(result) - 1]
    f.write(name + ';' + position + ';' + shoots + ';' + born + ';' + hometown + ';' +
            height + ';' + weight + ';' + high_school + ';' + college + ';' + draft +
            ';' + debut + ';' + experience + ';' + number + ';' + '\n')

    tables = re.findall(r'<div class="table_container[\s\S]*?" id="(.*?)">([\s\S]*?)</table>', html)
    for _name, table in tables:
        if _name == 'div_totals':
            f.write('Totals\n')
        elif _name == 'div_per_game':
            f.write('Per Game\n')
        elif _name == 'div_advanced':
            f.write('Advanced\n')
        elif _name == 'div_playoffs_totals':
            f.write('Playoffs Totals\n')
        elif _name == 'div_playoffs_per_game':
            f.write('Playoffs Per Game\n')
        elif _name == 'div_playoffs_advanced':
            f.write('Playoffs Advanced\n')
        else:
            continue
        tbody = re.findall(r'<tbody>([\s\S]*?)</tbody>', table)[0]
        trs = re.findall(r'<tr id[\s\S]*?>([\s\S]*?)</tr>', tbody)
        for tr in trs:
            season = re.findall(r'<a href="/players/.*?">(.*?)</a>', tr)[0][2:]
            team = re.search(r'<td class="left " data-stat="team_id" ><a href="/teams/.*?">(.*?)</a></td>', tr)
            if team is None:
                team = re.search(r'<td class="left " data-stat="team_id" >(.*?)</td>', tr).groups()[0]
            else:
                team = team.groups()[0]

            pos = re.search(r'<td class="center " data-stat="pos" >(.*?)</td>', tr).groups()[0]
            f.write(season + ';' + team + ';' + pos + ';')
            data = re.findall(r'<td class="right " .*?>(.*?)</td>', tr)
            for temp in data:
                f.write(temp + ';')
            f.write('\n')
        tr = re.findall(r'<tfoot>[\s\S]*?>([\s\S]*?)</tr>', table)[0]
        tds = re.findall(r'<td class="right "[\s\S]*?>(.*?)</td>', tr)
        f.write('Career;;')
        for td in tds:
            f.write(td + ';')
        f.write('\n')

    result = re.search(r'<div class="table_container" id="div_salaries">([\s\S]*?)</table>', html)
    if result is not None:
        f.write('Salaries\n')
        tables = result.groups()[0].replace('\n', '')
        result = re.search(r'<tbody>(.*?)</tbody>', tables).groups()[0]
        result = re.findall(r'<tr  class="">(.*?)</tr>', result)
        for item in result:
            temp = re.search(r'<td align="left" >(.*?)</td>', item)
            season = temp.groups()[0][2:]
            temp = re.search(r'<td align="left" ><a href="/teams/(.*?)/.*?</td>', item)
            team = temp.groups()[0]
            temp = re.search(r'<td align="right"  csk=".*?">(.*?)</td>', item)
            salary = temp.groups()[0]
            f.write(season + ';' + team + ';' + salary + ';\n')
        result = re.search(r'<tr  class=" stat_total">(.*?)</tr>', tables).groups()[0]
        season = re.search(r'<td align="left" >(.*?)</td>', result).groups()[0]
        salary = re.search(r'<td align="right".*?>(.*?)</td>', result).groups()[0]
        f.write(season + ';;' + salary + ';\n')
    f.close()
    print 'replayer is: ', replayer


def scrape_team():
    teams = get_team_list()
    for item in teams:
        scrape_each_team(item[1], item[0], item[2])
        time.sleep(0.2)


def scrape_team_by_local():
    f = open('data/teams/teams.txt', 'r')
    teams = f.readlines()
    for item in teams:
        item = item.replace('\n', '')
        temp = item.split(';')
        scrape_each_team(temp[0], temp[1], temp[2])
        time.sleep(0.2)


def scrape_each_team(name, abbr, year):
    print name, abbr
    f = open('data/teams/text/' + abbr, 'w')
    url = main_url + 'teams/' + abbr
    response = urllib2.urlopen(url, context=ssl_ctx)
    html = response.read()
    trs = re.search(r'<strong>Location:</strong>([\s\S]*?)</p>', html)
    location = trs.groups()[0].strip('\n ')
    record = re.search(r'Record:</strong>([\s\S]*?)</p>', html).groups()[0].strip('\n ')
    trs = re.search(r'\((.*?) NBA', record)
    if trs is None:
        record = re.search(r'(.*?),', record).groups()[0]
    else:
        record = trs.groups()[0]
    playoff_appearance = re.search(r'Playoff Appearances:</strong>([\s\S]*?)</p>', html).groups()[0].strip('\n ')
    trs = re.search(r'\((.*?) NBA', playoff_appearance)
    if trs is not None:
        playoff_appearance = trs.groups()[0]
    championships = re.search(r'Championships:</strong>([\s\S]*?)</p>', html).groups()[0].strip('\n ')
    trs = re.search(r'\((.*?) NBA', championships)
    if trs is not None:
        championships = trs.groups()[0]

    league = 'E'
    division = ''
    global nba_teams
    if len(nba_teams) == 0:
        url2 = 'https://www.nba.com/teams'
        html2 = urllib2.urlopen(url2, context=ssl_ctx).read()
        nba_teams = re.findall(r'class="TeamFigure_tfContent.*?">[\s\S]*?>([\s\S]*?)</a>[\s\S]*?<a href="(.*?)/" [\s\S]*?Profile', html2)

    for _name, url in nba_teams:
        if _name == name:
            resp = urllib2.urlopen('https://www.nba.com' + url, context=ssl_ctx).read()
            _league = re.search('<!-- -->East<!-- -->', resp)
            if _league is None:
                league = 'W'
    f.write(name + ';' + abbr + ';' + year + ';' + location + ';' + record + ';' +
            playoff_appearance + ';' + championships + ';' + league + ';' + division + ';\n')

    f.write('Totals\n')
    url = main_url + 'teams/' + abbr + '/stats_basic_totals.html'
    html = urllib2.urlopen(url, context=ssl_ctx).read()
    table = re.findall(r'<table class="sortable stats_table" id="stats" data-cols-to-freeze=",1">([\S\s]*?)</table>', html)[0]
    trs = re.findall(r'<tr >([\S\s]*?)</tr>', table)
    sites = []
    for tr in trs:
        data = re.findall(r'<t[hd][\s\S]*?>\s*<a\s*href="(.*?)"\s*>(.*?)</a>\s*</t[hd]>', tr)
        _site, season = data[0]
        season = season[2:]
        if 'BBA' in data[1] or 'ABA' in data[1]:
            break
        f.write(season + ';')
        sites.append([season, _site])
        count = 0
        tds = re.findall(r'<td class="right\s*"[\s\S]*?>(.*?)</td>', tr)
        for temp in tds[:6]:
            f.write(temp + ';')
            count += 1
        f.write('\n')

    for site in sites:
        f.write(site[0] + '\n')
        url = main_url + site[1][1:]
        print url
        html = urllib2.urlopen(url, context=ssl_ctx).read()
        table = re.findall(r'<table[\s\S]*?id="team_and_opponent"[\s\S]*?>([\S\s]*?)</table>', html)
        trs = re.findall(r'<tr[\s\S]*?>([\s\S]*?)</tr>', table[0])
        for tr in trs[1:]:
            item = re.findall(r'<t[hd][\s\S]*?>(.*?)</t[hd]>', tr)
            if item[0] == 'Team' or item[0] == 'Team/G' or item[0] == 'Opponent' or item[0] == 'Opponent/G':
                for temp in item[1:]:
                    f.write(temp + ';')
                f.write('\n')

        table = re.search(r'<div class=".*?" id="div_team_misc">([\S\s]*?)</div>', html).groups()[0]
        trs = re.findall(r'<tr[\s\S]*?>([\s\S]*?)</tr>', table)
        tds = re.findall(r'<td[\s\S]*?>(.*?)</td>', trs[2])
        for td in tds:
            f.write(td + ';')
        f.write('\n')

        time.sleep(0.2)
    f.close()


def get_team_list():
    f = open('data/teams/teams.txt', 'w')
    url = main_url + 'teams/'
    response = urllib2.urlopen(url, context=ssl_ctx)
    html = response.read()
    trs = re.findall(r'<tr class="full_table.*?" .*?>(.*?)</tr>', html)
    teams = []
    for tr in trs:
        print tr
        abbr, name, year = re.findall(r'href="/teams/(.*?)/">([A-z\d\\.\s]*)?</a>[\s\S]*year_min" >(.*?)<', tr)[0]
        teams.append([name, abbr, year[:4]])
        f.write(name + ';' + abbr + ';' + year[:4] + ';\n')
    f.close()
    return trs


def scrape_team_logo():
    f = open('data/teams/teams.txt', 'r')
    lines = f.readlines()
    for line in lines:
        temp = line.replace('\n', '').split(';')
        abbr = temp[1]
        print abbr
        url = 'http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/' + abbr + '.png'
        if abbr == 'NOH':
            url = 'http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/no.png'
        if abbr == 'UTA':
            url = 'http://a.espncdn.com/combiner/i?img=/i/teamlogos/nba/500/utah.png'
        response = urllib2.urlopen(url, context=ssl_ctx)
        f_logo = open('data/teams/logo/' + abbr + '.png', 'wb')
        f_logo.write(response.read())
        f_logo.close()
        time.sleep(0.2)


def scrape_player_now_pic():
    url = 'http://espn.go.com/nba/players'
    response = urllib2.urlopen(url, context=ssl_ctx)
    html = response.read()
    result = re.findall(r'<a style="padding-top:5px;padding-left:0px;" href="(.*?)">', html)
    for item in result:
        url = 'http://espn.go.com' + item
        response = urllib2.urlopen(url, context=ssl_ctx)
        html = response.read()
        results = re.findall(r'<td class="sortcell"><a href="(.*?)">(.*?)</a>', html)
        for temp in results:
            url = temp[0]
            name = temp[1]
            print name
            response = urllib2.urlopen(url, context=ssl_ctx)
            html = response.read()
            temps = re.search(r'<meta property="og:image" content="(.*?)&h=.*?" />', html)
            if temps is not None:
                url = temps.groups()[0]
                response = urllib2.urlopen(url, context=ssl_ctx)
                pic = open('data/players/now_pic/' + name + '.png', 'wb')
                pic.write(response.read())
                pic.close()
            time.sleep(0.2)
    pass


def scrape_player_pic():
    f_player = open('data/players/players.txt', 'r')
    players = f_player.readlines()
    for player in players:
        item = player.replace('\n', '').split(';')
        name = item[0]
        print name
        url = 'http://www.basketball-reference.com' + item[1]
        response = urllib2.urlopen(url, context=ssl_ctx)
        html = response.read()
        result = re.search(r'<img .*? src="(.*?)" alt=', html)
        if result is not None:
            url = result.groups()[0]
            response = urllib2.urlopen(url, context=ssl_ctx)
            f = open('data/players/pic/' + name + '.png', 'wb')
            f.write(response.read())
            f.close()
            time.sleep(0.2)


def create_file():
    if os.path.isdir('data'):
        pass
    else:
        os.mkdir('data')
    if os.path.isdir('data/matches'):
        pass
    else:
        os.mkdir('data/matches')
    if os.path.isdir('data/players'):
        pass
    else:
        os.mkdir('data/players')
    if os.path.isdir('data/players/text'):
        pass
    else:
        os.mkdir('data/players/text')
    if os.path.isdir('data/players/now_pic'):
        pass
    else:
        os.mkdir('data/players/now_pic')
    if os.path.isdir('data/players/pic'):
        pass
    else:
        os.mkdir('data/players/pic')
    if os.path.isdir('data/teams'):
        pass
    else:
        os.mkdir('data/teams')
    if os.path.isdir('data/teams/logo'):
        pass
    else:
        os.mkdir('data/teams/logo')
    if os.path.isdir('data/teams/text'):
        pass
    else:
        os.mkdir('data/teams/text')


if __name__ == '__main__':
    create_file()
    begin()
