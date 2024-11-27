def table_maker(number):
    for a in range(11):
        print(f"{a} * {number} = {a*number}")
    print("\n")
def run_tables(num,limit):
    count = 0
    while count<limit:
        table_maker(num)
        num+=1
        count+=1
num=1
run_tables(num,limit=1000)