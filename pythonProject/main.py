import time

def delete_lines(input_file_path, output_file_path, start_line, end_line):
    # 打开输入文件用于读取，输出文件用于写入
    with open(input_file_path, 'r', encoding='utf-8') as reader, open(output_file_path, 'w', encoding='utf-8') as writer:
        # 初始化行号计数器
        line_number = 0

        # 记录开始时间
        start_time = time.time()

        # 遍历输入文件中的每一行
        for line in reader:
            line_number += 1
            # 跳过在start_line到end_line范围内的行
            if start_line <= line_number <= end_line:
                continue

            # 将行写入输出文件
            writer.write(line)

        # 记录结束时间
        end_time = time.time()
        # 以毫秒为单位计算持续时间
        duration = (end_time - start_time) * 1000
        print(f"处理完成，耗时 {duration:.2f} 毫秒。")

# 定义文件路径和行范围
input_file_path = "D:/demo/danmu_input.csv"
output_file_path = "D:/demo/danmu1.csv"
start_line = 2
end_line = 300001

# 使用指定的参数调用函数
delete_lines(input_file_path, output_file_path, start_line, end_line)
