# MyBatis Helper

MyBatis用のXMLを出力するプラグインです。MapperファイルからXMLファイルへのジャンプ。またファイルが存在しない場合には、雛形の作成を行います。

インストールは更新サイトから行います。更新サイトは次のとおりです。

http://webarata3.github.io/mybatishelper

インストール後、プロジェクトのプロパティで設定を確認します。

![設定画面](http://webarata3.github.io/mybatishelper/image/preference.png)

ソースファイル、リソースファイルの場所は正しく設定しないと動作しません。

設定後、MapperのJavaファイルのどこでもいいので右クリックすると「対応するXMLを開く」メニューがありますのでクリックします。そうするとリソースファイルの場所に、ソースと同じパッケージ同じファイル名（拡張子はxml）のファイルが作成されます。すでにファイルが有る場合にはそのファイルが開かれます。

![XMLファイルの生成と移動](http://webarata3.github.io/mybatishelper/image/usemybatishelper.gif)

## ライセンス

MIT

