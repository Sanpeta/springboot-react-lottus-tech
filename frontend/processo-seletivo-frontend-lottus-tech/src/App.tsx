import { PrimeReactProvider } from 'primereact/api';
import OrderTable from './components/OrderTable';
import ProductTable from './components/ProductTable';

import 'primeicons/primeicons.css';
import 'primereact/resources/themes/lara-light-cyan/theme.css';

function App() {
	return (
		<>
			<PrimeReactProvider>
				<h1>Processo Seletivo Lottus Tech</h1>
				<h1>Produtos</h1>
				<ProductTable />
				<h1>Pedidos</h1>
				<OrderTable />
			</PrimeReactProvider>
		</>
	);
}

export default App;
