__author__ = 'joel'
import sys
"""Tools for analyze exception in log file."""


class ExLogUtil:
    def __init__(self):
        pass

    @staticmethod
    def read_log_file(path):
        try:
            file_content = open(path)
            while True:
                lines = file_content.readlines(1000)
                if not lines:
                    break
                temp_line = ""
                num = 1
                for line in lines:
                    level = ""
                    if len(line) > 26:
                        level = line[21:26].rstrip()
                    if "WARN" == level or "ERROR" == level:
                        print "%d%s%s" % (num, ": ", line),
                    elif "DEBUG" == level or "INFO" == level:
                        if len(temp_line) > 0:
                            print temp_line
                            temp_line = ""
                    else:
                        temp_line += str(num) + ": " + str(line)
                    num = num + 1
                if len(temp_line) > 0:
                    print temp_line,
        except Exception, e:
            print e,


def main():
    # if len(sys.argv) != 2:
    #     print "wrong args, example: " + sys.argv[0] + " [filename]"
    #     return
    file_name = sys.argv[1]
    # file_name = "../resource/test.log"
    ExLogUtil.read_log_file(file_name)


if __name__ == '__main__':
    main()
