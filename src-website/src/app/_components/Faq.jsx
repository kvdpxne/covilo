const faqs = [
  {
    id: 1,
    question: "Jak działa aplikacja do zgłaszania przestępstw",
    answer: "Nasza aplikacja umożliwia użytkownikom zgłaszanie przestępstw za pośrednictwem internetu. Po zalogowaniu się lub utworzeniu konta, użytkownicy mogą wypełnić formularz zgłoszeniowy, podając szczegóły przestępstwa, miejsce zdarzenia i inne istotne informacje. Zgłoszenie jest następnie przesyłane do odpowiednich organów ścigania w celu dalszego postępowania.",
  },
  {
    id: 2,
    question: "Czy moje zgłoszenie będzie poufne?",
    answer:
      "Tak, wszystkie zgłoszenia są traktowane jako poufne i przetwarzane zgodnie z obowiązującymi przepisami dotyczącymi ochrony danych osobowych. Dążymy do zapewnienia pełnej poufności i bezpieczeństwa dla naszych użytkowników.",
  },  {
    id: 3,
    question: "Czy mogę śledzić postęp w moim zgłoszeniu?",
    answer:
      "Tak, po złożeniu zgłoszenia użytkownicy otrzymują unikalny numer referencyjny, którym mogą śledzić postęp w swoim zgłoszeniu. Informacje o statusie zgłoszenia są regularnie aktualizowane, aby użytkownicy mogli być na bieżąco z postępem w dochodzeniu.",
  },  {
    id: 4,
    question: "Czy mogę zgłosić przestępstwo anonimowo?",
    answer:
      "Tak, nasza aplikacja umożliwia zgłaszanie przestępstw anonimowo. Chociaż zachęcamy do podawania jak najwięcej informacji, aby ułatwić dochodzenie, rozumiemy, że niektórzy użytkownicy mogą preferować anonimowość. Zapewniamy, że wszystkie zgłoszenia są traktowane równie poważnie, niezależnie od tego, czy są złożone anonimowo, czy też nie.",
  },
]

export default function Faq() {
  return (
    <div className="bg-white">
      <div className="mx-auto max-w-7xl px-6 py-24 sm:pt-32 lg:px-8 lg:py-40">
        <div className="lg:grid lg:grid-cols-12 lg:gap-8">
          <div className="lg:col-span-5">
            <h2
              className="text-2xl font-bold leading-10 tracking-tight text-gray-900">Frequently
              asked questions</h2>
            <p className="mt-4 text-base leading-7 text-gray-600">
              Can’t find the answer you’re looking for? Reach out to our{' '}
              <a href="#"
                 className="font-semibold text-indigo-600 hover:text-indigo-500">
                customer support
              </a>{' '}
              team.
            </p>
          </div>
          <div className="mt-10 lg:col-span-7 lg:mt-0">
            <dl className="space-y-10">
              {faqs.map((faq) => (
                <div key={faq.question}>
                  <dt
                    className="text-base font-semibold leading-7 text-gray-900">{faq.question}</dt>
                  <dd
                    className="mt-2 text-base leading-7 text-gray-600">{faq.answer}</dd>
                </div>
              ))}
            </dl>
          </div>
        </div>
      </div>
    </div>
  )
}