package basic.greedy;

import java.util.Arrays;

/**
 * 一些会议要占用一个会议室宣讲，会议室不能同时容纳两个会议的宣讲。
 * 给你每个项目的开始时间和结束时间，你来安排宣讲的日程，要求会议室进行宣讲的场次最多。
 * 返回最多的宣讲场次数量。
 * <p>
 * 微信公众号：Java和算法学习
 *
 * @author 周一
 */
public class BestArrange {

    public static class Meeting {
        public int start;
        public int end;

        public Meeting(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    /**
     * 假设会议开始时间和结束时间都是 大于0 的数值
     */
    public static int bestArrange(Meeting[] meetings) {
        // 所有会议先根据结束时间按从小到大排序
        Arrays.sort(meetings, (o1, o2) -> o1.end - o2.end);
        // 已经安排的会议数量
        int result = 0;
        // 当前所处时间点
        int timeLine = 0;
        // 遍历所有会议，结束时间早的已经在前
        for (Meeting meeting : meetings) {
            // 剩下会议的开始时间在此时会议结束时间之后，则安排剩下会议中的第一个会议
            if (meeting.start >= timeLine) {
                result++;
                timeLine = meeting.end;
            }
        }
        return result;
    }

    /**
     * 对数器方法
     */
    public static int bestArrange1(Meeting[] meetings) {
        if (meetings == null || meetings.length == 0) {
            return 0;
        }
        return process(meetings, 0, 0);
    }

    /**
     * 目前来到timeLine时间点，已经安排了done个会议，剩下的会议meetings可以自由安排
     *
     * @param meetings 还剩下的会议
     * @param done     之前已经安排的会议数量
     * @param timeLine 目前来到的时间点
     * @return 能安排的最多会议数量
     */
    public static int process(Meeting[] meetings, int done, int timeLine) {
        // 没有剩余的会议
        if (meetings.length == 0) {
            return done;
        }
        // 还有剩余会议
        int max = done;
        // 当前安排的会议是什么会，每一个都枚举
        for (int i = 0; i < meetings.length; i++) {
            // 当前会议的开始时间在所有已经安排会议的结束时间点之后，则当前会议可以安排
            if (meetings[i].start >= timeLine) {
                // 移除已经安排的会议，即移除当前会议
                Meeting[] next = removeIndex(meetings, i);
                // 再在剩下的会议中求最大能安排的会议数量
                max = Math.max(max, process(next, done + 1, meetings[i].end));
            }
        }
        return max;
    }

    public static Meeting[] removeIndex(Meeting[] meetings, int i) {
        Meeting[] ans = new Meeting[meetings.length - 1];
        int index = 0;
        for (int k = 0; k < meetings.length; k++) {
            if (k != i) {
                ans[index++] = meetings[k];
            }
        }
        return ans;
    }

    public static void main(String[] args) {
        int meetingSize = 12;
        int timeMax = 24;
        int timeTimes = 1000000;
        for (int i = 0; i < timeTimes; i++) {
            Meeting[] meetings = generatePrograms(meetingSize, timeMax);
            if (bestArrange(meetings) != bestArrange1(meetings)) {
                System.out.println("Error!");
                break;
            }
        }
        System.out.println("Finish!");
    }

    // ---------------------------------- 辅助测试的方法 ---------------------------------

    public static Meeting[] generatePrograms(int meetingSize, int timeMax) {
        Meeting[] ans = new Meeting[(int) (Math.random() * (meetingSize + 1))];
        for (int i = 0; i < ans.length; i++) {
            int r1 = (int) (Math.random() * (timeMax + 1));
            int r2 = (int) (Math.random() * (timeMax + 1));
            if (r1 == r2) {
                ans[i] = new Meeting(r1, r1 + 1);
            } else {
                ans[i] = new Meeting(Math.min(r1, r2), Math.max(r1, r2));
            }
        }
        return ans;
    }

}
