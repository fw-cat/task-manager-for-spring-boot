# slim系のnodeから構築することを定義
FROM node:16.17.1-bullseye
# コンテナ内の作業ディレクトリを作成
WORKDIR /app
# パッケージ一覧(インデックス)を更新
RUN apt update \
    && yarn install
# ポート番号5173でコンテナを公開
EXPOSE 5173