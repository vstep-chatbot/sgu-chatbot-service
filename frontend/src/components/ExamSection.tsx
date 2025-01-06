interface ExamSectionProps {
  title: string;
  children: React.ReactNode;
}

export function ExamSection({ title, children }: ExamSectionProps) {
  return (
    <div className="mb-6">
      <h4 className="mb-3 text-lg font-semibold text-gray-800">{title}</h4>
      {children}
    </div>
  );
}
