import { createFileRoute } from "@tanstack/react-router";
import { Section } from "../components/Section";
import { ExamSection } from "../components/ExamSection";

export const Route = createFileRoute("/")({
  component: VstepInformationPage,
});

function VstepInformationPage() {
  return (
    <div className="mx-auto max-w-4xl space-y-8 p-6">
      <header className="mb-8">
        <h1 className="text-3xl font-bold text-gray-900">VSTEP</h1>
      </header>

      <div className="prose prose-slate max-w-none space-y-6">
        <Section>
          <p>
            <strong>VSTEP</strong> là định dạng đề thi đánh giá năng lực tiếng
            Anh từ bậc 3 đến bậc 5 theo Khung năng lực ngoại ngữ 6 bậc dùng cho
            Việt Nam (còn gọi là Vietnamese Standardized Test of English
            Proficiency, viết tắt là VSTEP).
          </p>
          <p className="mt-4">
            <strong>VSTEP</strong> được xây dựng nhằm trở thành một công cụ đánh
            giá năng lực tiếng Anh từ bậc 3 – 5 cho đối tượng sau trung học phổ
            thông, được sử dụng trong phạm vi toàn quốc và hướng tới được quốc
            tế công nhận. Đây cũng là mục tiêu của Đề án Ngoại ngữ 2020. VSTEP
            được áp dụng đối với cơ sở đào tạo ngoại ngữ, chương trình đào tạo
            ngoại ngữ, và người học ngoại ngữ trong hệ thống giáo dục quốc dân.
          </p>
        </Section>

        <Section title="Thang điểm bài thi VSTEP">
          <p>
            Mỗi bài thi VSTEP.3-5 được chấm trên thang điểm 10 theo từng kỹ
            năng, làm tròn đến 0.5. Điểm làm tròn của 4 kỹ năng làm tròn đến 0.5
            được dùng để quy ra 3 bậc tương ứng B1, B2, C1. Dưới 4.0 sẽ không
            xét bậc trình độ, 4.0/10 đạt B1, 6.0/10 đạt B2, 8.5/10 đạt C1.
          </p>
          <p className="mt-4">
            Đối với bài thi VSTEP A2 đánh giá 1 trình độ tiếng Anh A2. Điểm tính
            trên thang điểm 100 quy về 10. Mỗi kỹ năng chiếm 25% tổng số điểm.
            Thí sinh đạt trình độ tiếng Anh A2 nếu đạt 6.5/10.
          </p>
        </Section>

        <Section title="Cấu trúc bài thi VSTEP">
          <ExamSection title="1/ Kỹ năng nghe hiểu:">
            <ul className="list-disc space-y-2 pl-5 text-gray-700">
              <li>
                Thời gian làm bài 40 phút đã bao gồm 7 phút để chuyển câu trả
                lời sang phiếu trả lời.
              </li>
              <li>Số câu hỏi cho cả 3 phần là 35 câu hỏi trắc nghiệm.</li>
              <li>
                Các thí sinh được nghe các đoạn trao đổi ngắn, hướng dẫn, thông
                báo, các bài giảng, bài nói chuyện, sau đó trả lời câu hỏi trắc
                nghiệm (MCQ) đã in sẵn trong đề thi.
              </li>
              <li>
                Nhằm mục đính kiểm tra đánh giá các kỹ năng nghe khác nhau, có
                độ khó từ bậc 3 đến bậc 5.
              </li>
            </ul>
          </ExamSection>

          <ExamSection title="2/ Kỹ năng đọc hiểu:">
            <ul className="list-disc space-y-2 pl-5 text-gray-700">
              <li>
                Phần đọc hiểu trong bài thi VSTEP với thời gian 60 phút, số
                lượng 4 bài đọc – 40 câu hỏi. Nhiệm vụ của thí sinh là đọc cả 4
                đoạn văn và trả lời câu hỏi sau mỗi bài đọc. Độ khó của văn bản
                tương đương bậc 3-5 với tổng số từ dao động từ 1.900-2.050 từ.
              </li>
              <li>
                Phần này nhằm kiểm tra đánh giá kỹ năng đọc khác nhau, từ mức độ
                bậc 3 đến bậc 5, kỹ năng đọc hiểu, hiểu ý kiến, thái độ tác giả
                được suy ra từ thông tin trong bài và đoán nghĩa của từ trong
                văn cảnh.
              </li>
            </ul>
          </ExamSection>

          <ExamSection title="3/ Kỹ năng viết:">
            <ul className="list-disc space-y-2 pl-5 text-gray-700">
              <li>
                Bài 1: Yêu cầu thí sinh viết một bức thư/ thư điện tử có độ dài
                khoảng 120 từ. Bài 1 chiếm 1/3 tổng số điểm của phần thi viết.
              </li>
              <li>
                Bài 2: Thí sinh cần viết bài luận khoảng 250 từ về một chủ đề
                cho sẵn thông qua kiến thức trải nghiệm thực tế của mình đưa ra
                lập luận và bảo vệ ý kiến của mình. Bài viết chiến 2/3 tổng điểm
                toàn phần thi viết.
              </li>
            </ul>
          </ExamSection>

          <ExamSection title="4/ Kỹ năng nói:">
            <p className="mb-4">
              Trong một bài thi VSTEP kỹ năng nói là một kỹ năng không kém phần
              quan trọng, đánh giá khă năng giao tiếp của các bạn, ngữ pháp, vốn
              từ vựng… thí sinh phải trả lời từ 03 đến 06 câu hỏi thuộc 02 chủ
              đề khác nhau.
            </p>
            <ul className="list-disc space-y-2 pl-5 text-gray-700">
              <li>
                Phần nói 1: Thí sinh được kiểm tra khă năng miêu tả và giải
                thích.
              </li>
              <li>
                Phần nói 2: Ở phần nói này các thí sinh được cung cấp một tình
                huống và được cho sẵn 3 giải pháp. Thí sinh phải đưa ra giải
                pháp tốt nhất, bảo vệ lập luận của mình và phản biện những ý
                kiến khác.
              </li>
              <li>
                Phần nói 3: Thí sinh được cho một chủ đề cho sẵn, có thể sử dụng
                các ý đã được cung cấp để phát triển thành bài nói hoàn chỉnh.
                Phần 3 kết thúc với một số câu hỏi thảo luận về chủ đề trên.
              </li>
            </ul>
          </ExamSection>
        </Section>

        <Section title="Chứng chỉ VSTEP có thời hạn bao lâu?">
          <p>
            Đối với chứng chỉ tiếng Anh VSTEP, trên chứng chỉ sẽ không ghi rõ
            thời hạn, hạn sử dụng phụ thuộc vào cơ quan, doanh nghiệp, cơ sở đào
            tạo yêu cầu.
          </p>
          <p className="mt-4">
            Ví dụ: Theo thông tư đào tạo thạc sĩ, chứng chỉ sẽ có thời hạn 2 năm
            tính từ ngày thi.
          </p>
          <p className="mt-4">
            Các đơn vị khác nếu không có yêu cầu cụ thể thì coi như chứng chỉ
            VSTEP có giá trị vĩnh viễn.
          </p>
        </Section>
      </div>
    </div>
  );
}
