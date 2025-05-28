# spocomi-frontend

## 概要
スポコミは、スポーツ用具や施設の**備品貸出**をきっかけに、人と人をつなぎ、新しいコミュニティ形成を後押しするプラットフォームです。貸出窓口が単なる予約管理の場ではなく、地域の「ハブ」となり、利用者同士の出会いと協働を促進。小さな運動グループが互いに連携し合い、継続的なスポーツ活動と地域交流を支えます。

## 主な機能
- **備品・施設予約**  
  利用者は空き状況を見ながら、必要な用具や場所を簡単に予約できます。

- **コミュニティ作成・参加**  
  予約時に同じ日時・場所を利用するメンバー同士を自動マッチング。参加希望を募集することで、自然な形で仲間づくりが可能です。

- **ハブ機能によるネットワーク形成**  
  貸出窓口を起点に、複数の小規模コミュニティを相互に紹介・連携。合同練習や交流イベントを通じて、より大きなネットワークを構築します。

- **継続支援**  
  活動ログやリマインダー機能により、運動習慣の定着をサポートします。

## 技術スタック
- Spring Boot 2.7.0
- Thymeleaf
- Java 11
- LINE Login認証
- RESTful APIとの連携

## プロジェクト構成
本リポジトリは、スポコミのフロントエンド部分を管理しています。

### 主要コンポーネント
- **UIコントローラー**: ユーザーインターフェースの制御、LINE認証、画面遷移
- **HTMLテンプレート**: Thymeleafを使用したレスポンシブUIコンポーネント
- **静的リソース**: CSS、JavaScript、画像ファイル
- **APIクライアント**: バックエンドサービスとの通信を担当

### 主要機能の実装
- **コミュニティ管理機能**  
  コミュニティの作成、編集、メンバー管理などの機能を提供します。ユーザーは自分のコミュニティを作成し、他のユーザーを招待することができます。

- **イベント管理機能**  
  コミュニティ内でのイベント作成、参加管理、スケジュール機能を提供します。イベントの検索や参加申し込みもこちらから行えます。

- **予約システム**  
  施設や備品の予約機能を提供します。空き状況の確認や予約の管理が可能です。

- **コミュニティネットワーキング**  
  複数のコミュニティ間の連携を支援する機能です。関連するコミュニティの発見や合同イベントの企画を促進します。

- **LINE認証連携**  
  LINEアカウントを使用したユーザー認証を提供します。シームレスなログイン体験を実現します。

## セットアップ手順

### 前提条件
- Java 11
- Gradle
- LINE Developersアカウント

### 設定手順
1. リポジトリをクローンします
```
git clone https://github.com/dx-junkyard/spocomi-frontend.git
cd spocomi-frontend
```

2. LINE認証の設定
   - LINE Developers consoleで新しいチャンネルを作成します（LINEログインとMessaging API）
   - 設定ファイルを準備します：
```
# src/main/resources/application.yml_commit を src/main/resources/application.yml にコピー
cp src/main/resources/application.yml_commit src/main/resources/application.yml
```
   - application.ymlを編集し、LINE認証情報を設定します：
```yaml
line-login:
  client_id: "LINE Developers consoleで作成したチャンネルのチャネルID"
  client_secret: "LINE Developers consoleで作成したチャンネルのチャネルシークレット"
  login_redirect_uri: "あなたのサーバーURL/v1/auth"

backend-api:
  uri: "バックエンドAPIのエンドポイント（例：http://127.0.0.1:8080）"
```

3. プロジェクトをビルド・実行します
```
./gradlew bootRun
```

4. ブラウザで以下のURLにアクセスします
```
http://localhost:8080/v1/user/line-login
```

