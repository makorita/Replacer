〇RDB型リプレーサ
<rdb:機器名@ID=1010>
使用禁止文字列:@,=,<,>

〇XMLリプレーサ
<xml:[xPath]>

XML作成メソッド
タブ区切り文字の最後の文字列がバリューで途中がパスになる。
aaa:bbb=ccc,ddd=eeee
┗アトリビュート追加
基本ノードがある場合はそれに追加される。

〇Multiリプレーサ
ReplaceMap.txtにタブ区切りで書かれた文字列に従って
文字列を置換。3列目がregularの場合は正規表現置換

〇Repeater
<repeat:#A5-10>
</repeat:#A5-10>
#Aは3桁0埋めで繰り返される

〇ListReplacer
<list>
