import pandas as pd
import psycopg2
from psycopg2 import sql
import time


def get_count_from_csv2(host, dbname, username, password, csv_file_path, count_max):
    start = time.time()
    # 使用 pandas 读取 CSV 文件
    df = pd.read_csv(csv_file_path)
    bvs = df['bv'].head(count_max).tolist()

    if not bvs:
        print(0)
        return

    # 使用 psycopg2 连接 PostgreSQL 数据库
    conn = psycopg2.connect(
        database="postgres",
        user="postgres",
        password="",
        host="127.0.0.1",
        port="5432")
    cur = conn.cursor()

    # 为 SQL 查询创建占位符列表
    placeholders = ', '.join(['%s'] * len(bvs))

    try:


        # 执行 SQL 查询
        query = sql.SQL("SELECT COUNT(*) AS total FROM danmu WHERE danmu_BV IN ({})").format(sql.SQL(placeholders))
        cur.execute(query, bvs)

        # 获取结果
        total_count = cur.fetchone()[0]

        end = time.time()
        elapsed_time = end - start
        print("总共耗时: {} 毫秒".format(elapsed_time * 1000))

    except psycopg2.Error as e:
        print(e)
    finally:
        cur.close()
        conn.close()

    print(total_count)


# 示例用法（用您的实际数据库连接细节和 CSV 文件路径替换占位符）:
get_count_from_csv2('localhost', 'postgres', 'postgres', '', 'D:/downloadnormal/data/videos1.csv', 10000)
