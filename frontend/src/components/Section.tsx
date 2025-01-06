interface SectionProps {
  title?: string;
  children: React.ReactNode;
}

export function Section({ title, children }: SectionProps) {
  return (
    <section className="mb-8">
      {title && (
        <h2 className="mb-4 text-2xl font-bold text-gray-900">{title}</h2>
      )}
      <div>{children}</div>
    </section>
  );
}
